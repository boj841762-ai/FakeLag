# FakeLag Client — Minecraft 1.8.9 (Forge)

Лёгкий и оптимизированный клиент на базе **Forge 1.8.9**. Работает как Forge-мод, поэтому
рядом можно ставить любые другие моды для 1.8.9.

## Возможности

- **ClickGUI на `Right Shift`** — панели по категориям, перетаскивание, настройки модулей, бинды.
  Клавишу можно сменить в *Настройки → Управление → FakeLag Client*.
- **Sprint (AutoSprint)** — режимы `Legit` (держит клавишу спринта, безопасно на серверах) и `Vanilla`.
- **FPSBoost** — ограничение FPS, когда окно не в фокусе; быстрый пресет графики (VBO, FBO, Fast,
  без облаков и теней сущностей).
- **EntityCulling** — не рендерит далёких мобов и ники (настраиваемая дистанция).
- **HUD** — watermark, FPS, координаты, список включённых модулей.
- Оптимизированные флаги JVM (G1GC, низкие паузы) при запуске через `run.bat`.
- Конфиг сохраняется автоматически в `config/fakelag.json`.

## Управление ClickGUI

| Действие | Что делает |
|---|---|
| ЛКМ по модулю | Включить / выключить |
| ПКМ по модулю | Раскрыть настройки |
| СКМ по модулю или строка `Bind` | Назначить клавишу (`Esc`/`Delete` — снять бинд) |
| ЛКМ по заголовку | Перетащить панель (ПКМ — свернуть) |
| Колёсико | Прокрутка панелей |
| `Right Shift` / `Esc` | Закрыть |

## Запуск (Windows)

| Файл | Назначение |
|---|---|
| `run.bat` | Запускает Minecraft 1.8.9 + Forge + клиент. При первом запуске сам скачивает портативные JDK 17 и Gradle в `.tools\` и саму игру (несколько минут). |
| `build.bat` | Собирает готовый `.jar` в `build\libs\`. |
| `install.bat` | Собирает и копирует `.jar` в `%APPDATA%\.minecraft\mods` для обычного лаунчера с профилем Forge 1.8.9. |

**Моды:** положите `.jar` моды для Forge 1.8.9 в папку `mods/` — `run.bat` подключит их автоматически.

> `run.bat` запускает игру с оффлайн-ником (одиночная игра и сервера без лицензии).
> Для лицензионного аккаунта используйте `install.bat` и обычный лаунчер с Forge 1.8.9
> (`forge-1.8.9-11.15.1.2318`).

## Структура

```
FakeLag/
├─ run.bat / build.bat / install.bat
├─ scripts/bootstrap.bat          # скачивание JDK 17 + Gradle
├─ mods/                          # сюда дополнительные моды 1.8.9
├─ build.gradle.kts, settings.gradle.kts, gradle.properties
└─ src/main/java/com/fakelag/client/
   ├─ FakeLagClient.java          # @Mod, бинд RSHIFT
   ├─ module/                     # Module, Category, ModuleManager
   │  └─ impl/                    # Sprint, Hud, FpsBoost, EntityCulling
   ├─ setting/                    # Boolean / Number / Mode настройки
   ├─ gui/ClickGui.java           # ClickGUI
   └─ config/ConfigManager.java   # сохранение в JSON
```

## Добавить свой модуль

1. Создайте класс в `module/impl`, унаследуйте `Module`, переопределите `onTick()` / `onEnable()` / `onDisable()`.
2. Добавьте настройки через `register(new BooleanSetting(...))`.
3. Зарегистрируйте модуль в конструкторе `ModuleManager`.
Он сразу появится в ClickGUI и в конфиге.

## Сборка вручную

Нужна JDK 17 (для Gradle) — JDK 8 для игры скачается автоматически:
`gradle build` → `build/libs/FakeLag-1.0.0.jar`, `gradle runClient` — запуск.
