import { Pizza } from './Pizza';

export interface OrderedPizza {
    orderPizza: number;
    prepNum: number;
    pizza: Pizza;
}