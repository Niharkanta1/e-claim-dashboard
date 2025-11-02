import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { tap } from "rxjs/operators";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private baseUrl = "http://localhost:9091/api/auth";
  private currentUserSubject = new BehaviorSubject<any>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, { username, password }).pipe(
      tap((res: any) => {
        console.log("Response: ", res);
        localStorage.setItem("token", res.token);
        this.decodeAndSetUser(res.token);
      })
    );
  }

  decodeAndSetUser(token: string) {
    const payload = JSON.parse(atob(token.split(".")[1]));
    const role =
      payload && payload.roles && payload.roles.length
        ? payload.roles[0].authority.replace("ROLE_", "")
        : "";
    const username = payload.sub;
    const user = { username, role, token };
    this.currentUserSubject.next(user);
  }

  logout() {
    localStorage.removeItem("token");
    this.currentUserSubject.next(null);
  }

  getCurrentUser() {
    return this.currentUserSubject.value;
  }

  getCurrentUserSubject() {
    return this.currentUserSubject;
  }

  isLoggedIn() {
    return !!localStorage.getItem("token");
  }
}
