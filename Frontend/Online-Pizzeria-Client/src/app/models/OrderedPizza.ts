export interface OrderedPizza {
    id: number;
    number: number;
    name: string;
    ingredients: {name: string}[];
}