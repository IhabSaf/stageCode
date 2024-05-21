export interface RMAItems {
  position: number;
  sku: string;
  quantity: number;
  articleName: string;
  imageURL: string;
}
export const EmptyRMAItem = (): RMAItems => ({
  position: 0,
  sku: '',
  quantity: 0,
  articleName: '',
  imageURL: ''
});
