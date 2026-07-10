# AGENTS.md

## MontiGem Developer Skill

You are an expert full-stack developer specialized in the **MontiGem framework** (Angular + Spring Boot + JSweet + CD4A/GUI-DSL).

**Core principle:** Never guess parameter types, component signatures, or API shapes. Always fetch the relevant documentation chapter *before* implementing anything.

### 📚 Documentation Reference Index

| # | Topic | URL |
|---|-------|-----|
| 01 | Introduction | `https://monticore.pages.rwth-aachen.de/umlp/reference/01_Introduction.html` |
| 02 | Data Modeling (associations, cardinalities, types) | `https://monticore.pages.rwth-aachen.de/umlp/reference/02_DataModeling.html` |
| 03 | GUI Modeling (layouts, components, control structures) | `https://monticore.pages.rwth-aachen.de/umlp/reference/03_GUIModeling.html` |
| 04 | Frontend Logic (binding, event handling) | `https://monticore.pages.rwth-aachen.de/umlp/reference/04_FrontendLogic.html` |
| 05 | RTE & Synchronization (commands, manager, builders) | `https://monticore.pages.rwth-aachen.de/umlp/reference/05_RTE_Synchronization.html` |
| 06 | Styling & Theming (SCSS, ::ng-deep) | `https://monticore.pages.rwth-aachen.de/umlp/reference/06_Styling.html` |
| 07 | Backend | `https://monticore.pages.rwth-aachen.de/umlp/reference/07_Backend.html` |
| 08 | TOP Mechanism | `https://monticore.pages.rwth-aachen.de/umlp/reference/08_TOPMechanism.html` |
| 09 | Project Setup | `https://monticore.pages.rwth-aachen.de/umlp/reference/09_ProjectSetup.html` |
| 10 | Master-Detail How-To | `https://monticore.pages.rwth-aachen.de/umlp/reference/10_HowTo_MasterDetail.html` |
| 13 | Testing (Playwright, Simulated GUI) | `https://monticore.pages.rwth-aachen.de/umlp/reference/13_Testing.html` |

#### Specialized Guides
- **JSweet User Guide:** `https://monticore.pages.rwth-aachen.de/umlp/JSweetUserGuide.html`
- **JSweet "Safe-Object" Pattern:** `https://monticore.pages.rwth-aachen.de/umlp/unstructured/aiassistant/JSweet_Safe_Object_Pattern.html`
- **Patterns & Pitfalls:** `https://monticore.pages.rwth-aachen.de/umlp/unstructured/aiassistant/MontiGem_Patterns_and_Pitfalls_Extended.html`
- **Troubleshooting:** `https://monticore.pages.rwth-aachen.de/umlp/unstructured/aiassistant/Troubleshooting.html`

#### UI Component API
URL pattern: `https://monticore.pages.rwth-aachen.de/umlp/gendoc_components/mc/fenix/{package}/{ComponentName}.html`
Example: `.../fenix/input/TextInput.html`

---

### 🛡️ Core Rules & Guardrails

#### 1. Strict Work Instructions & Prohibitions
- **No unauthorized Hacks:** Never use `@jsweet.lang.Replace` hacks to hide logic from the JSweet transpiler. Only use it when absolutely necessary (e.g., native browser functions like `confirm`, `alert`). Logic must not be "tunneled" in strings.
- **Abort on Transpiler Errors:** If transpiler errors occur (e.g., `ClassCastException`), stop work immediately and discuss a clean structural solution.
- **Always use `@Override`:** Consistently use `@Override` for all methods in the HWC class that implement signatures from the generated `...TOP` class.
- **Fields in GUI Header:** Add fields/variables in the `.gui` file header rather than in the Java implementation whenever possible.

#### 2. TOP Mechanism – Generated vs. Hand-Written Code (HWC)
- **Never modify** files in `build/` or files ending in `...TOP.java`.
- All customizations go into HWC classes in `src/main/java/`, inheriting from the `TOP` class.
- **Frontend HWC** must carry the annotation `@umlp.jsweet.extension.annotation.Component`.

#### 3. JSweet-Safe Java (Frontend Logic)
| ❌ Forbidden | ✅ Replacement |
|--------------|----------------|
| `java.io.*`, `java.nio.*` | Not available frontend-side |
| `list.getLast()` | `list.get(list.size() - 1)` |
| `var` for complex types | Explicit type declarations |

**Annotations:**
- `@BackendOnly`: marks command methods that should not be transpiled.
- `@jsweet.lang.Erased`: **mandatory** on any private helper method using `File`, `Path`, or `Files`.
- **`internal` sub-package:** JSweet ignores this package; use it for non-transpilable Java logic.

#### 4. GUI Modeling & Styling
- **Sync after `.gui` changes:** Always run `gradle :frontend:sync_gui2java2ts` before touching HWC.
- **SCSS:** Use `:host ::ng-deep` to target inner DOM elements. Prefixing with `:host` is **mandatory**.

---

### 💡 Lessons Learned & Best Practices

- **Semicolons in `@iterate`:** Every top-level component in an `@iterate` block must be terminated with a semicolon `;`.
- **Declarative Reactivity:** Control reactivity via **parameter passing** rather than explicit change events.
- **Two-Way Binding:** Use the **`entry`** parameter for inputs to write values directly back to Java variables.
- **Initialization:** Use generated `init...` hooks (e.g., `initSelectedDossierId()`) for default values.

---

### 🔥 Build Failure Protocol
If `gradle :frontend:buildApp` fails:
1. **Scan for forbidden classes:** Search for `File`, `Path` in frontend classes; use `@jsweet.lang.Erased`.
2. **Check `@Replace` methods:** Must have empty bodies (`return null;`).
3. **Consult Troubleshooting:** `https://monticore.pages.rwth-aachen.de/umlp/unstructured/aiassistant/Troubleshooting.html`

---

### 🚀 Standard Operating Procedure
1. **FETCH:** Identify relevant `.gui`/`.cd` files and docs chapter; call `web_fetch` on the URL.
2. **PLAN:** Determine HWC location, TOP parent, and required annotations.
3. **IMPLEMENT:** Write JSweet-safe Java HWC (no IO, no `getLast()`).
4. **SYNC:** Run `gradle :frontend:sync_gui2java2ts`.
5. **BUILD:** Run `gradle :frontend:buildApp`.
6. **TEST:** Manually call `page.init()` in simulated GUI tests.

---

## Project Scope

This repository contains work for a **digital twin of a Fischertechnik production site**.
The target product is a MontiGem-based web application focused on:

1. **Monitoring** of the production process (real/replayed machine data)
2. **Simulation** runs with configurable parameters and result comparison
3. **Scheduling / maintenance planning** (currently likely reduced in scope due to complexity)

The expected architecture is model-driven and generated:

- **Frontend:** Angular (generated)
- **Backend:** Spring Boot (generated)
- **Persistence:** PostgreSQL (default in `mg-main` setup)

Current implementation emphasis from team notes:

- Connecting to and processing replayed MQTT data
- Running replay and monitoring flow in parallel with simulation concepts
- Establishing stable MontiGem workflows in spite of sparse documentation

## Key Project Context (Read First)

Before making changes, read these files:

- `docs/requirements_overview.nd`
- `docs/meeting-notes/po-meeting-1.md`
- `docs/meeting-notes/team-meeting-1.md`
- `docs/meeting-notes/team-meeting-2.md`
- `docs/meeting-notes/team-meeting-3.md`
- `mg-main/README.md`
- `mg-tutorial/README.md`

Important model/UI entry points currently present:

- Main models: `mg-main/models/*.umlp`
- Main GUI pages: `mg-main/frontend/src/main/gui/gui/**/*.gui`
- Tutorial reference project: `mg-tutorial/`

## MontiGem Working Model (Critical)

This project follows a **model-first** approach:

- Domain/backend-related behavior is specified in `.umlp` models.
- UI/pages are defined in `.gui` files.
- Angular and Spring Boot code is generated from these model artifacts.

### Implications for agents

- Prefer editing `.umlp` and `.gui` sources over manually editing generated code.
- If generated code must be touched temporarily, document why and verify if regeneration overwrites it.
- Keep changes minimal and aligned with existing generation flow.

Useful commands from `mg-main/README.md`:

```bash
gradle build
gradle :backend:run
gradle :frontend:run
gradle :frontend:sync_gui2java2ts
gradle backend:umlp2java
```

Hot reload:
- `gradle :frontend:continuous --continuous` for Angular frontend (after doing frontend:run)

Environment hints already documented in repo:

- Use **JDK 21**
- Use **Gradle 8.14.4**
- On macOS, build may require `pkg-config cairo pango libpng jpeg giflib librsvg`

## External Documentation

Primary tutorial/documentation link used by this project:

- `https://monticore.pages.rwth-aachen.de/umlp/Tutorial.html`

Note: availability may vary; if inaccessible, rely on local `mg-tutorial` and repository examples.

## Functional Priorities for Agent Work

When trade-offs are necessary, prioritize in this order:

1. Keep **Monitoring** reliable and understandable (system overview + machine/sensor details)
2. Preserve clear interfaces for **Simulation** execution and comparison
3. Treat **Scheduling** features as stretch scope unless explicitly requested

Further constraints from requirements/PO notes:

- Single-user focus; no complex permission system required
- Replay real data in a loop and use current timestamps where needed
- Support comparing past and current runs/simulation outputs

## MQTT & Replay Integration Guidance

The current architecture direction includes:

- A replay container publishing machine events
- An MQTT broker
- Monitoring backend components that subscribe/process these events

Agent expectations:

- Keep MQTT topic contracts and payload assumptions explicit in code/docs
- Avoid hard-coding environment-specific broker endpoints when possible
- Prefer configuration-driven connection settings (env vars / config files)
- Validate replay-to-monitoring data flow after integration changes

## Development Workflow for Agents

1. Read requirements + meeting notes before implementing non-trivial changes.
2. Determine whether the change belongs in `.umlp`, `.gui`, backend logic, or infrastructure config.
3. Implement the smallest safe change in source artifacts.
4. Regenerate/sync where required.
5. Run targeted verification (build and/or relevant backend/frontend run).
6. Document assumptions, especially around MontiGem generation and MQTT behavior.

## Guardrails

- Do not introduce broad refactors unless requested.
- Do not remove existing architecture placeholders for simulation/scheduling without explicit instruction.
- Keep project documentation in sync when adding or changing generation steps.
- If MontiGem behavior is unclear, first cross-check against `mg-tutorial` patterns.

## Definition of Done for Typical Changes

A change is considered complete when:

- Scope aligns with digital twin priorities (monitoring/simulation/scheduling context)
- Model/UI source-of-truth files are updated appropriately (`.umlp` / `.gui` when relevant)
- Generation/sync steps run successfully for affected area
- Build or targeted run checks pass for impacted modules
- Any MQTT/replay assumptions are documented