export interface Pizza {
    id: number;
    name: string;
    price: number;
    ingredients: {name: string}[];
    picture?: string;
    discount_percent?: number;
}
