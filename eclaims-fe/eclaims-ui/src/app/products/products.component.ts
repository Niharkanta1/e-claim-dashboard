import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-products",
  templateUrl: "./products.component.html",
  styleUrls: ["./products.component.scss"],
})
export class ProductsComponent implements OnInit {
  products = [
    {
      name: "Product A",
      description: "High-quality item for everyday use.",
      image: "https://via.placeholder.com/150",
    },
    {
      name: "Product B",
      description: "Reliable and durable design for professionals.",
      image: "https://via.placeholder.com/150",
    },
    {
      name: "Product C",
      description: "Affordable and efficient, great value for money.",
      image: "https://via.placeholder.com/150",
    },
  ];

  constructor() {}

  ngOnInit() {}
}
