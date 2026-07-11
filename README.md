# No Crystal Hitbox

A small **client-side** [Fabric](https://fabricmc.net/) mod for Minecraft that lets you toggle
the rendering of **End Crystal hitbox outlines** with a key press.

## What it does

- Press **H** (rebindable in *Options → Controls → No Crystal Hitbox*) to toggle whether End
  Crystal hitboxes are drawn.
- **Hitboxes are hidden by default.**
- Each time you press the key, a short status message appears **above the hotbar for 5 seconds**:
  - **`Crystal Hitbox Hide Active`** — shown in **green** when hitboxes are hidden (default).
  - **`Crystal Hitbox Not active`** — shown in **red** when hitboxes are visible.

## Requirements

This mod **requires [Fabric API](https://modrinth.com/mod/fabric-api)** — it will not load without it.

| Component        | Version               |
|------------------|-----------------------|
| Minecraft        | 1.21.11               |
| Fabric Loader    | 0.19.3 or newer       |
| Fabric API       | 0.141.4+1.21.11       |
| Java             | 21 or newer           |

Install both this mod **and** Fabric API into your `mods` folder.

## Building from source

```bash
./gradlew build
```

The finished jar will be in `build/libs/`.

## Porting to another Minecraft version

The mod deliberately avoids mixins and uses only stable Fabric API events, so porting is easy:

1. Edit **`gradle.properties`** and update `minecraft_version`, `yarn_mappings`, `loader_version`,
   `loom_version` and `fabric_version`. Matching versions are listed at
   <https://fabricmc.net/develop>.
2. Update the version bounds in **`src/main/resources/fabric.mod.json`** (`minecraft`, `java`) and the
   `it.options.release` / `sourceCompatibility` values in **`build.gradle`** if the target version
   needs a different Java release.
3. The only rendering call that may differ between versions lives in
   **`CrystalHitboxRenderer.java`** (`DebugRenderer.drawBox`). If it fails to compile after a version
   bump, that single line is the place to fix.

## License

[MIT](LICENSE)
