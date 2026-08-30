import { Component, OnInit } from "@angular/core";

@Component({
    selector: "app-products",
    templateUrl: "./products.component.html",
    styleUrls: ["./products.component.scss"],
    standalone: false
})
export class ProductsComponent implements OnInit {
  products = [
    {
      name: "products.productA",
      description: "products.productADescription",
      image: "https://via.placeholder.com/150",
    },
    {
      name: "products.productB",
      description: "products.productBDescription",
      image: "https://via.placeholder.com/150",
    },
    {
      name: "products.productC",
      description: "products.productCDescription",
      image: "https://via.placeholder.com/150",
    },
  ];

  constructor() {}

  ngOnInit() {}
}
