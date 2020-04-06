export interface Pizza {
    id: number;
    name: string;
    price: number;
    ingredients: string[];
    picture?: string;
    discount_price?: number;
}