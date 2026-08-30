import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';

import { AuthGuard } from './auth.guard';
import { AuthService } from '../service/auth.service';

describe('AuthGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
    providers: [AuthGuard, { provide: AuthService, useValue: { isLoggedIn: jasmine.createSpy() } }, { provide: Router, useValue: { navigate: jasmine.createSpy() } }]
});
  });

  it("allows logged-in users", () => {
    const auth = TestBed.inject(AuthService) as any;
    auth.isLoggedIn.and.returnValue(true);
    expect(TestBed.inject(AuthGuard).canActivate({} as any, {} as any)).toBeTrue();
  });

  it("redirects logged-out users to login", () => {
    const auth = TestBed.inject(AuthService) as any;
    const router = TestBed.inject(Router) as any;
    auth.isLoggedIn.and.returnValue(false);
    expect(TestBed.inject(AuthGuard).canActivate({} as any, {} as any)).toBeFalse();
    expect(router.navigate).toHaveBeenCalledWith(["/login"]);
  });
});
