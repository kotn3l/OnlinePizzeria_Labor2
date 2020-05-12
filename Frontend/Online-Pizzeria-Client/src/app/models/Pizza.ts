export interface Pizza {
    id: number;
    name: string;
    price: number;
    ingredients: ({name: string}|string)[];
    picture?: string;
    discount_percent?: number;
}
