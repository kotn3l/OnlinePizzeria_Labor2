export class OrderForDelivery {
    order_id: number
    city: string
    street: string
    house_number: string
    other: string
    pizza_count?: number
    pizzas?: string[]
}