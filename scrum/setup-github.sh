#!/bin/bash

# ============================================================
# setup-github.sh
# Cria labels, milestones (sprints) e issues no GitHub
# usando o GitHub CLI (gh).
#
# Uso:
#   ./setup-github.sh -r "seu-usuario/seu-repositorio"
# ============================================================

REPO=""

while getopts r: flag
do
    case "${flag}" in
        r) REPO=${OPTARG};;
    esac
done

if [ -z "$REPO" ]; then
    echo "Uso: ./setup-github.sh -r 'usuario/repositorio'"
    exit 1
fi

set -e

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &> /dev/null && pwd)
CONFIG_DIR="$SCRIPT_DIR/config"
LABELS_FILE="$CONFIG_DIR/labels.json"
MILES_FILE="$CONFIG_DIR/milestones.json"
BACKLOG_FILE="$CONFIG_DIR/backlog.json"

echo -e "\n=== Verificando pre-requisitos ==="

if ! command -v gh &> /dev/null; then
    echo "[ERRO] GitHub CLI (gh) nao encontrado."
    exit 1
fi

if ! gh auth status &> /dev/null; then
    echo "[ERRO] Nao autenticado no GitHub CLI. Execute: gh auth login"
    exit 1
fi

if ! command -v jq &> /dev/null; then
    echo "[ERRO] jq nao encontrado. Instale com 'brew install jq' no Mac."
    exit 1
fi

echo -e "GitHub CLI autenticado com sucesso."
echo -e "Repositorio alvo: $REPO\n"

# 1. LABELS
echo -e "=== Criando labels ==="
jq -c '.[]' "$LABELS_FILE" | while read -r label; do
    name=$(echo "$label" | jq -r '.name')
    color=$(echo "$label" | jq -r '.color')
    description=$(echo "$label" | jq -r '.description')
    
    if gh label create "$name" --color "$color" --description "$description" --repo "$REPO" --force &>/dev/null; then
        echo "  [OK] Label: $name"
    else
        echo "  [ERRO] Label: $name"
    fi
done

# 2. MILESTONES
# 2. MILESTONES
echo -e "\n=== Criando milestones (sprints) ==="
MS_MAP_FILE=$(mktemp)

while read -r ms; do
    title=$(echo "$ms" | jq -r '.title')
    description=$(echo "$ms" | jq -r '.description')
    due_on=$(echo "$ms" | jq -r '.due_on')
    
    # Tenta criar e se existir falha
    response=$(gh api "repos/$REPO/milestones" -X POST -f title="$title" -f description="$description" -f state="open" -f due_on="$due_on" --silent 2>/dev/null || true)
    
    if [ -n "$response" ] && [ "$(echo "$response" | jq -r '.number')" != "null" ]; then
        number=$(echo "$response" | jq -r '.number')
        echo "$title|$number" >> "$MS_MAP_FILE"
        echo "  [OK] Milestone: $title (#$number)"
    else
        # Já existe, busca número
        existing=$(gh api "repos/$REPO/milestones" --paginate | jq -r --arg title "$title" '.[] | select(.title==$title) | .number')
        if [ -n "$existing" ] && [ "$existing" != "null" ]; then
            echo "$title|$existing" >> "$MS_MAP_FILE"
            echo "  [OK] Milestone ja existe: $title (#$existing)"
        else
            echo "  [ERRO] Milestone: $title"
        fi
    fi
done < <(jq -c '.[]' "$MILES_FILE")

# 3. ISSUES
echo -e "\n=== Criando issues do backlog ==="
jq -c '.[]' "$BACKLOG_FILE" | while read -r sprint; do
    msTitle=$(echo "$sprint" | jq -r '.milestone')
    
    echo -e "\n  >> $msTitle"
    
    echo "$sprint" | jq -c '.issues[]' | while read -r issue; do
        title=$(echo "$issue" | jq -r '.title')
        body=$(echo "$issue" | jq -r '.body')
        labels=$(echo "$issue" | jq -r '.labels | join(",")')
        
        args=(issue create --repo "$REPO" --title "$title" --body "$body" --label "$labels")
        if [ -n "$msTitle" ]; then
            args+=(--milestone "$msTitle")
        fi
        
        if gh "${args[@]}" &>/dev/null; then
            echo "  [OK] $title"
        else
            echo "  [ERRO] $title"
        fi
    done
done

rm -f "$MS_MAP_FILE"

# 4. PROJECT
echo -e "\n=== Criando GitHub Project (Kanban) ==="
projectName="Migracao para Web - Spring Boot e Angular"
owner="${REPO%/*}"

if gh project create --owner "$owner" --title "$projectName" &>/dev/null; then
    echo "  [OK] Projeto criado: $projectName"
else
    echo "  [AVISO] Nao foi possivel criar o projeto automaticamente ou ele já existe."
fi

echo -e "\n=== Configuracao concluida! ==="
echo "  https://github.com/$REPO/issues"
echo "  https://github.com/$REPO/milestones"
echo "  https://github.com/$REPO/projects"
