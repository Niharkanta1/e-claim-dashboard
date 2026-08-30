# Angular Upgrade Plan

## Current State

- Current Angular framework version: `20.3.30`
- Current Angular CLI and build tooling: `20.3.35`
- Current TypeScript version: `5.8.3`
- Current RxJS version: `7.8.1`
- Node requirement in `package.json`: `>=22.0.0`
- Current application style: NgModule-based
- Current build configuration: Angular application builder (`@angular/build`)
- Current unit-test runner: Karma
- Current end-to-end configuration: Protractor files are present, but the setup should be verified
- Current development port: `4300`

The project has been upgraded to Angular 20. The Angular 21 and 22 upgrades remain future work. Recheck the exact published versions and compatibility requirements immediately before continuing.

## Upgrade Strategy

Upgrade one Angular major version at a time. The completed migration used Angular's official update schematics and preserved the existing NgModule application structure.

Target sequence:

1. [x] Angular 17 to 18
2. [x] Angular 18 to 19
3. [x] Angular 19 to 20
4. Angular 20 to 21
5. Angular 21 to 22
6. Post-upgrade cleanup and modernization

Do not manually mix Angular packages from different major versions.

## Phase 0: Baseline

The Angular 17 baseline production build passed. It produced the existing Bootstrap selector warning and an unused `environment.prod.ts` warning.

The initial commands must be run from this directory:

```powershell
npm ci
npm run build
npm test
npm list @angular/core @angular/cli @angular-devkit/build-angular
node --version
npm --version
```

The test script was subsequently made one-shot so it runs Karma in headless mode and exits cleanly.

Manually verify the current behavior of:

- Login and logout
- Authenticated route protection
- Customer, manager, adjuster, and partner dashboards
- Claim list, claim details, and claim creation
- Overview and reports screens
- API calls and JWT interception

## Phase 1: Angular 17 to 18

```powershell
npx ng update @angular/core@18 @angular/cli@18
npm install
npm run build
npm test
```

Completed with Angular framework packages at `18.2.14` and CLI/build tooling at `18.2.21`. The optional application-builder migration was accepted. The Angular core migration also replaced deprecated HTTP modules with provider functions.

## Phase 2: Angular 18 to 19

```powershell
npx ng update @angular/core@19 @angular/cli@19 --allow-dirty
npm install
npm run build
npm test
```

Completed with Angular framework packages at `19.2.25` and CLI/build tooling at `19.2.27`. The migration marked the existing NgModule components with `standalone: false`. The Angular 19 build passed.

## Phase 3: Angular 19 to 20

```powershell
npx ng update @angular/core@20 @angular/cli@20 --allow-dirty
npm install
npm run build
npm test
```

Completed with Angular framework packages at `20.3.30`, CLI/build tooling at `20.3.35`, TypeScript at `5.8.3`, and `zone.js` at `0.15.1`. The migration updated TypeScript module resolution, moved the workspace to the application builder, and changed the test `polyfills` setting to the required array format.

The test command was updated in `package.json` to `ng test --watch=false --browsers=ChromeHeadless` because the original nested script left Karma in watch mode. After that fix, the existing guard spec passed with `1 SUCCESS`. The Angular 20 production build also passed.

## Phase 4: Angular 20 to 21

```powershell
ng update @angular/core@21 @angular/cli@21
npm install
npm run build
npm test -- --watch=false --browsers=ChromeHeadless
```

Run the application and repeat the authentication, dashboard, claim, and reporting smoke tests before creating the checkpoint.

## Phase 5: Angular 21 to 22

Recheck the currently published versions and Angular 22 compatibility requirements first:

```powershell
npm view @angular/core version
npm view @angular/cli version
```

Then run:

```powershell
ng update @angular/core@22 @angular/cli@22
npm install
npm run build
npm test -- --watch=false --browsers=ChromeHeadless
```

Confirm that the installed Node.js and TypeScript versions are supported by Angular 22. If the Angular update recommends a specific Node range, update the `engines` field accordingly.

## Package Alignment

After the Angular 20 migration, all Angular packages were verified on the compatible `20.3.x` line:

```powershell
npm list @angular/animations @angular/common @angular/compiler @angular/compiler-cli @angular/core @angular/forms @angular/language-service @angular/platform-browser @angular/platform-browser-dynamic @angular/router @angular/cli @angular-devkit/build-angular
```

The following packages must remain aligned:

- `@angular/animations`
- `@angular/common`
- `@angular/compiler`
- `@angular/compiler-cli`
- `@angular/core`
- `@angular/forms`
- `@angular/language-service`
- `@angular/platform-browser`
- `@angular/platform-browser-dynamic`
- `@angular/router`
- `@angular/cli`
- `@angular-devkit/build-angular` (the project now uses the migrated `@angular/build` application builder)

## Configuration Modernization

The Angular migrations updated `angular.json` from the legacy browser builder to the application builder, changed `main` to `browser`, and changed the build output to `dist/eclaims-ui/browser` by default. The existing explicit polyfills and manually configured Bootstrap JavaScript bundle remain in use.

Do not combine a standalone-component migration with the major-version upgrade. The existing NgModule structure in `src/app/app.module.ts` can continue to work on Angular 22. Consider standalone migration later as a separate change with its own tests.

## Testing and Tooling Follow-up

- Keep Karma if the current unit tests remain stable.
- Verify whether the Protractor end-to-end configuration is still used.
- Replace Protractor with Playwright or Cypress as a separate task if end-to-end coverage is required.
- Add or preserve coverage for auth guards, claim creation, claim viewing, dashboards, and API error handling.

## Documentation Cleanup

Update `README.md` after the upgrade because it currently references Angular CLI `8.3.29`, the default port `4200`, and the deprecated `--prod` option.

Document:

- Final Angular version
- Required Node.js and npm versions
- Development command and URL
- Build and test commands
- End-to-end test status

## Final Verification

Run a clean install and the complete validation set:

```powershell
npm ci
npm run build
npm test -- --watch=false --browsers=ChromeHeadless
npm audit
```

Then manually test the full application workflow at `http://localhost:4300` and confirm that the production build completes within its configured bundle budgets.

## Completion Checklist

- [x] Angular 20 baseline production build passed
- [x] Angular 18 migration complete
- [x] Angular 19 migration complete and build validated
- [x] Angular 20 migration complete and build validated
- [ ] Angular 21 migration complete and checkpointed
- [ ] Angular 22 migration complete and checkpointed
- [x] Angular package versions aligned on `20.3.x`
- [x] Angular 20 production build passes
- [x] Angular 20 unit tests pass (`1 SUCCESS`)
- [ ] Authentication and role-based workflows smoke-tested
- [ ] README updated
- [ ] Protractor status decided
- [ ] Final clean install verified
