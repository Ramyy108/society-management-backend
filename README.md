🏢 SOCIETY MANAGEMENT BACKEND (Spring Boot) -

This is my backend project for managing a housing society.
I built it to learn Spring Boot, PostgreSQL, Supabase, real APIs, authentication, deployment, and also how to structure a real backend system.
It includes features like user registration, login, complaints, notices, flats, and monthly bills.
Even though I’m still learning, I tried to make this project as complete and practical as possible.
<br>
<br>

🚀 FEATURES -

👤 User & Auth :

• Register Owner

• Register Admin

•Login

•Password hashing

•Fake-token authentication (simple version for learning)
<br>

🏠 Flats :

•Basic flat details are stored (seeded using DataInitializer)
<br>

📢 Notices :

•Create, update, delete, fetch notices (Admin)
<br>

🧾 Bills :

•Generate monthly bills

•Mark bill as paid

•Owner-wise bill history
<br>

🛠 Complaints :

•Owners can raise complaints

•Admin can resolve them
<br>
<br>


🛠 TECH STACK -

•Java 17

•Spring Boot 3

•Spring Data JPA

•PostgreSQL (Supabase)

•Render (Deployment)

•Postman (API testing)

•Maven
<br>
<br> 

🌐 DEPLOYMENT -

I deployed the backend on Render (free tier).
⚠️ Free tier sleeps after some time, so first request may take a few seconds.
<br>
<br>

🗄 DATABASE (Supabase) -

I used Supabase PostgreSQL as my cloud database.
Tables created:

✅users

✅flats

✅complaints

✅bills

✅notices

Sample Database on Supabase -

<img width="926" height="290" alt="image" src="https://github.com/user-attachments/assets/b0ad0bc2-f9f3-44b6-b07b-352631a84550" />

<br>
<br>
<br>
<br>
📬 API TESTING (Postman) -

I tested these APIs on Postman.

✔ Base URL
https://society-management-backend-qqme.onrender.com

✔ Test 1: Root API
GET "/" 
<img width="1707" height="893" alt="Screenshot 2025-12-11 182244" src="https://github.com/user-attachments/assets/b172aa7c-e6e9-4414-9706-47b028cc5a59" />
<br>


✔ Test 2: Register Owner
POST /api/auth/register-owner
<img width="1698" height="872" alt="Screenshot 2025-12-11 182654" src="https://github.com/user-attachments/assets/53f15794-11e0-4685-9062-cdbaca4c5f5f" />
<br>


✔ Test 3: Login
POST /api/auth/login
<img width="1695" height="876" alt="Screenshot 2025-12-11 182755" src="https://github.com/user-attachments/assets/f05c8be6-4231-4f9f-a658-7d8162e85679" />
<br>


✔ Test 4: Complaint API (requires token)
POST /api/complaints
<img width="1721" height="881" alt="Screenshot 2025-12-11 184118" src="https://github.com/user-attachments/assets/6290b1b7-2ed4-45b5-b1bc-4e63c3962e4c" />

<br>
<br>

⚠ KNOWN ISSUES -

1. Render Free Tier:
•After some time, the backend goes to sleep.
When inactive, first request is slow or may fail once.
2. Supabase Free Tier:
•Allows very few active connections → sometimes causes
max client connections reached.
3. Some APIs show 403 on Render:
•Because I used fake-token based authentication just for learning, not full JWT.
<br>
<br>

🔧 HOW TO RUN LOCALLY? -

1. Clone the repo:
"git clone <https://github.com/Ramyy108/society-management-backend>"
2. Update application.properties with your PostgreSQL or use H2
3. Run:
"mvn spring-boot:run"
4. Test APIs on Postman
<br>
<br>

📘 WHAT I LEARNED FROM THIS PROJECT? -


✅How to structure a real Spring Boot project

✅How authentication works

✅How to design entities & relationships

✅How to handle exceptions

✅How to use Supabase PostgreSQL

✅API testing with Postman

✅How deployment works on Render

✅Debugging production issues
<br>
<br>

🌱 FUTURE IMPROVEMENTS -

•Implement proper JWT authentication

•Add role-based access

•Add more society features

•Add integration tests

•Replace Supabase with local PostgreSQL
<br>
<br>

👤 AUTHOR -

Ramkisan Yadav

3rd Year Computer Engineering Student

Mumbai, India
