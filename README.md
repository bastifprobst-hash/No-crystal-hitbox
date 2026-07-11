# No Crystal Hitbox

A small **client-side** [Fabric](https://fabricmc.net/) mod for Minecraft that **hides End
Crystal hitboxes in the F3 + B debug hitbox view**, toggleable with a key press.

## What it does

When you turn on Minecraft's hitbox display with **F3 + B**, *every* entity shows its hitbox —
including End Crystals, which can clutter the view (e.g. in crystal PvP). This mod lets you hide
just the End Crystal hitboxes.

- Press **H** (rebindable in *Options → Controls → No Crystal Hitbox*) to toggle End Crystal
  hitboxes in the F3 + B view.
- End Crystal hitboxes are **hidden by default**.
- Each time you press the key, a short status message appears **above the hotbar for 5 seconds**:
  - **`Crystal Hitbox Hide Active`** — shown in **green** when crystal hitboxes are hidden (default).
  - **`Crystal Hitbox Not active`** — shown in **red** when crystal hitboxes are visible again.

> The effect is only visible while the F3 + B hitbox display is enabled — that is what draws
> hitboxes in the first place. This mod simply removes the End Crystal ones from that view.

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

## How it works

Vanilla draws entity hitboxes in `EntityHitboxDebugRenderer#drawHitbox`. A small Mixin
(`EntityHitboxDebugRendererMixin`) cancels that call for `EndCrystalEntity` while the hidden
state is active. No custom rendering is done, so the hidden/shown crystal hitboxes look exactly
like the vanilla ones.

## Porting to another Minecraft version

1. Edit **`gradle.properties`** and update `minecraft_version`, `yarn_mappings`, `loader_version`,
   `loom_version` and `fabric_version`. Matching versions are listed at
   <https://fabricmc.net/develop>.
2. Update the version bounds in **`src/main/resources/fabric.mod.json`** and the Java `release`
   values in **`build.gradle`** if the target version needs a different Java release.
3. The only version-sensitive spot is the Mixin target `drawHitbox` in
   **`EntityHitboxDebugRendererMixin.java`**. If the method is renamed in another version, update
   the `method = "..."` reference there.

## License

[MIT](LICENSE)
