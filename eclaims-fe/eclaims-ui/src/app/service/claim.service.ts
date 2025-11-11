import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class ClaimService {
  private baseUrl = "http://localhost:9091/api/claims"; // adjust as per backend

  constructor(private http: HttpClient) {}

  createClaim(request: ClaimRequest): Observable<any> {
    const formData = new FormData();
    Object.entries(request).forEach(([key, value]) => {
      if (key === "files" && Array.isArray(value)) {
        value.forEach((f) => formData.append("files", f));
      } else if (value != null) {
        formData.append(key, value as string);
      }
    });
    return this.http.post(this.baseUrl, formData);
  }

  getAllClaims(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  getClaimById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  getLatestClaim(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/-1`);
  }

  assignUserToClaim(claimId: number, userId: number, role: string) {
    return this.http.put(
      `${this.baseUrl}/assign/${claimId}?userId=${userId}&role=${role}`,
      {}
    );
  }

  removeUserToClaim(claimId: number, role: string) {
    return this.http.put(
      `${this.baseUrl}/remove-assign/${claimId}?&role=${role}`,
      {}
    );
  }

  updateClaim(id: any, status: string): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}?status=${status}`, {});
  }
}

export interface ClaimRequest {
  firstName: string;
  lastName: string;
  policyNumber: string;
  policyType: string;
  dateOfIncident: string;
  claimType: string;
  description: string;
  contactNumber: string;
  files: File[];
}
