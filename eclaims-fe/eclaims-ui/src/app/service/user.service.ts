import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class UserService {
  private baseUrl = "http://localhost:9091/api/users";

  constructor(private http: HttpClient) {}

  getUsersByRole(role: string) {
    return this.http.get<any[]>(`${this.baseUrl}?userRole=${role}`);
  }
}
