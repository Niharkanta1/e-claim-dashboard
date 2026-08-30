# e-claim-dashboard

Java: 17.0.12
Node: v22.21.1

#### SCREENSHOTS:
<img width="1919" height="900" alt="{2DC9AC1A-D372-457E-A9CE-0E94B8B2E619}" src="https://github.com/user-attachments/assets/39c285c6-b99a-4c5d-bf05-1450009e2b11" />
<img width="1902" height="852" alt="{529FCC91-003C-4437-8E70-0B0D0C0DA342}" src="https://github.com/user-attachments/assets/e2c61071-85a3-4ede-87e6-598c2d6bdd4b" />
<img width="1903" height="854" alt="{A23E2738-525C-4D28-A350-77F622B44F23}" src="https://github.com/user-attachments/assets/098c6dfe-419a-4c82-bc2f-d211541bb425" />
<img width="1916" height="751" alt="{98E2E70F-F1C6-4BFE-B0AF-A40E74DBCEC5}" src="https://github.com/user-attachments/assets/25db75bb-c21e-42fb-9540-2979e183332a" />
<img width="1920" height="699" alt="{57F915C6-FEBC-4D9A-B944-BCDFCE9D74D7}" src="https://github.com/user-attachments/assets/cd157a9e-e9fa-4afd-a08a-bea785dea6a1" />
<img width="1908" height="784" alt="{C2339D52-5608-4F58-AFA1-76F701F3A40B}" src="https://github.com/user-attachments/assets/ac8424a3-3705-4ace-977c-846c20c03277" />
<img width="1895" height="785" alt="{E0D308BC-B5A4-44AC-B51B-DEDD87C1FAAF}" src="https://github.com/user-attachments/assets/d6daae33-d315-4551-a27f-a888d66ad650" />
<img width="1895" height="818" alt="{9B2E05E1-E73C-45F0-AF4E-AB1D3325784A}" src="https://github.com/user-attachments/assets/466b390d-51b4-4075-a021-e6b3ab60ef83" />
<img width="1889" height="723" alt="{5A925D99-431A-4261-9622-CB79B696A028}" src="https://github.com/user-attachments/assets/25982e70-d430-4a41-85b5-438084b99f6a" />
<img width="1904" height="838" alt="{6FF8988E-E453-4E4E-8A95-B78340A6B5B1}" src="https://github.com/user-attachments/assets/2617598a-5cac-4de5-b9d5-6789b83576b8" />
<img width="1920" height="791" alt="{9162E9F2-7479-4352-92B6-6AE4ED0FABE5}" src="https://github.com/user-attachments/assets/6b98f311-2a47-409f-8faf-50061cda4e73" />

Start eclaims-be:
```
mvn clean install
mvn spring-boot:run
```
- The Backend will be started at port: 9091
---
Start eclaims-fe:
```
npm install
npm start
```
- The UI will be started at port: 4300
- URL: http://localhost:4200/login

---
Database -> Persistent H2 databse
- Console: http://localhost:9091/h2-console
- File: jdbc:h2:file:./data/eclaimdb
- username: sa
---
### Users:
There are user role based login. Use the following credentials.
| Username | Role     | AreaCode |
|----------|----------|----------|
| suresh   | ADJUSTER | 1101     |
| ramesh   | ADJUSTER | 1101     |
| hitesh   | ADJUSTER | 1102     |
| ritesh   | ADJUSTER | 1102     |
| nihar    | CUSTOMER | 1101     |
| ravi     | CUSTOMER | 1102     |
| meera    | MANAGER  | 1101     |
| priya    | MANAGER  | 1102     |
| kanta    | PARTNER  | 1101     |
| santa    | PARTNER  | 1101     |
| anil     | PARTNER  | 1102     |
| sunil    | PARTNER  | 1102     |

password: password
