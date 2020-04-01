export interface Pizza {
    id: number;
    name: string;
    price: number;
    ingredients: string[];
    image?: string;
    discount_price?: number;
}