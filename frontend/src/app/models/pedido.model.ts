export interface ItemPedidoDTO {
  id?: number;
  produtoId: number;
  produtoNome?: string;
  quantidade: number;
  precoUnitario?: number;
}

export interface Pedido {
  id?: number;
  nome: string;
  statusPedidoId: number;
  statusPedidoNome?: string;
  itens?: ItemPedidoDTO[];
  valorTotal?: number;
}
