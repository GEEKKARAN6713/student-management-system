# Deploy & GitHub Guide (Beginner Friendly)

This guide covers three goals:

1. Run the **new web UI** on your PC  
2. Push the project to **GitHub**  
3. Go **live on the internet** with a **custom domain**

---

## Part A — Run the web UI locally

### Step 1: Create `application.properties`

Copy the example file:

```powershell
cd C:\Users\Karan\Projects\student-management-system
copy src\main\resources\application.properties.example src\main\resources\application.properties
```

Edit `application.properties` and set your MySQL password (same as before).

> If you only had `database.properties` before, copy the URL, username, and password into the `spring.datasource.*` fields.

### Step 2: Start MySQL and ensure schema exists

Your database `student_management` and tables must already exist (`sql/schema.sql`).

### Step 3: Run in IntelliJ

1. Open the project in IntelliJ.  
2. Open `SmsApplication.java`.  
3. Right-click → **Run 'SmsApplication'**.  
4. Open a browser: **http://localhost:8080**

You should see the student list with a modern dark UI.

### Step 4: Run with Maven (optional)

```powershell
mvn spring-boot:run
```

---

## Part B — Put the project on GitHub

### Step 1: Create a GitHub account

Sign up at https://github.com if you do not have an account.

### Step 2: Create a new empty repository

1. Click **+** → **New repository**.  
2. Name: `student-management-system`  
3. **Public** (so recruiters can see it).  
4. Do **not** add README, .gitignore, or license (you already have them locally).  
5. Click **Create repository**.

### Step 3: Push your code from PowerShell

Replace `YOUR_GITHUB_USERNAME` with your real username:

```powershell
cd C:\Users\Karan\Projects\student-management-system

git status
git add .
git commit -m "Add Spring Boot web UI and deployment files"

git branch -M main
git remote add origin https://github.com/YOUR_GITHUB_USERNAME/student-management-system.git
git push -u origin main
```

If Git asks you to sign in, use a **Personal Access Token** (not your password):

1. GitHub → **Settings** → **Developer settings** → **Personal access tokens**  
2. Generate token with `repo` scope  
3. Use the token as the password when pushing  

### Step 4: Make the repo look professional

On GitHub, edit the repo:

- **About**: short description + website URL after deploy  
- **Topics**: `java`, `spring-boot`, `jdbc`, `mysql`, `student-management`  
- Pin the repo on your profile  

Add a screenshot:

1. Run the app locally.  
2. Take a screenshot of http://localhost:8080/students  
3. Save as `docs/screenshot.png` in the project, commit, push.  
4. In README, link: `![App screenshot](docs/screenshot.png)`

---

## Part C — Go live on the internet

Your app needs:

| Piece | What it is |
|-------|------------|
| **Hosting** | Runs your Java app 24/7 |
| **Cloud MySQL** | Database on the internet (not only on your laptop) |
| **Domain** | Name like `mysms.com` (optional but professional) |

### Recommended beginner stack

| Service | Role | Cost |
|---------|------|------|
| **Railway** or **Render** | Host Java app | Free tier / low cost |
| **Railway MySQL** or **Aiven** | Cloud database | Free trial / starter |
| **Namecheap** or **Cloudflare** | Buy domain | ~$10–15/year |

> Your laptop MySQL is **not** reachable from the cloud. You must create a **new cloud database** and run `schema.sql` there once.

---

### Option 1 — Railway (easiest for beginners)

#### 1. Cloud database

1. Go to https://railway.app and sign in with GitHub.  
2. **New Project** → **Provision MySQL**.  
3. Open MySQL → **Connect** → copy variables: host, port, user, password, database.  
4. Build JDBC URL:

```text
jdbc:mysql://HOST:PORT/railway?useSSL=true&serverTimezone=UTC
```

5. Use MySQL Workbench or Railway’s query tab to paste and run `sql/schema.sql` (change `USE student_management` or create DB as Railway names it).

#### 2. Deploy the app

1. **New** → **GitHub Repo** → select `student-management-system`.  
2. Railway detects Dockerfile automatically.  
3. **Variables** (Settings → Variables):

| Variable | Value |
|----------|--------|
| `SPRING_DATASOURCE_URL` | your JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | from Railway |
| `SPRING_DATASOURCE_PASSWORD` | from Railway |
| `PORT` | `8080` |

Spring Boot maps these to `spring.datasource.*` automatically.

4. Deploy → copy public URL like `https://student-management-system-production.up.railway.app`

#### 3. Custom domain on Railway

1. Buy a domain (Namecheap, Google Domains, etc.).  
2. Railway project → **Settings** → **Networking** → **Custom Domain**.  
3. Add `www.yourdomain.com`.  
4. Railway shows DNS records (CNAME).  
5. At your domain registrar, add those DNS records.  
6. Wait 5–60 minutes for DNS to propagate.

---

### Option 2 — Render + external MySQL

1. Host MySQL on Railway or Aiven.  
2. Push repo to GitHub.  
3. https://render.com → **New Web Service** → connect repo.  
4. Runtime: **Docker** (uses included `Dockerfile`).  
5. Set environment variables same as Railway table above.  
6. **Custom domain**: Render dashboard → your service → **Settings** → **Custom Domains**.

---

## Part D — Environment variables (production)

Never commit passwords. On the host, set:

```text
SPRING_DATASOURCE_URL=jdbc:mysql://...
SPRING_DATASOURCE_USERNAME=...
SPRING_DATASOURCE_PASSWORD=...
PORT=8080
```

Spring Boot converts `SPRING_DATASOURCE_URL` → `spring.datasource.url`.

---

## Part E — Checklist before sharing on LinkedIn / resume

- [ ] GitHub repo is **public**  
- [ ] README has setup steps and screenshot  
- [ ] Live URL works (register + list students)  
- [ ] Custom domain optional but impressive  
- [ ] No passwords in Git (`application.properties` is gitignored)

---

## Troubleshooting

| Problem | Fix |
|---------|-----|
| App starts but DB error | Wrong JDBC URL or schema not created in cloud DB |
| 502 on Render/Railway | Check deploy logs; confirm `PORT` is set |
| Domain not working | Wait for DNS; verify CNAME at registrar |
| Git push rejected | `git pull --rebase origin main` then push again |

---

## Cost summary (typical)

| Item | Approx. yearly |
|------|----------------|
| Domain | $10–15 |
| Hosting (hobby) | $0–5/month |
| Cloud MySQL | $0–7/month on starter plans |

**Total to go live:** about **$10–20 for the domain** plus optional hosting after free tiers.
