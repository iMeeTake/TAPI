# Welcome to TLib

**TLib** (Take's Lib) is a utility-focused library for **Minecraft Fabric, Forge, and NeoForge mods**.

It provides powerful tools to make your mod cleaner, simpler, and more maintainable.  
Whether you're dealing with particles, sound, rendering, or other systems — TLib has you covered.

---

## 🎯 What you can do with TLib

With TLib, you can easily:

- 🎇 Spawn and control particles (static, dynamic, shaped, directional)
- 🧠 Register custom particle factories
- 🔉 Play one-time or looped sounds
- 🎥 Access camera view, FOV, and player perspective
- 🌦️ Query biome, weather, night/day cycle
- and much more

> 🧩 **TLib is under active development and new utilities are added regularly.**

---

## 📦 Gradle Setup (GitHub Maven)

Add the Maven repository to your `repositories` block:

```gradle
repositories {
	maven {
		name = "iMeeTake GitHub Maven"
		url = "https://raw.githubusercontent.com/iMeeTake/tlib-maven/main/"
	}
}

dependencies {
    // Fabric 1.21.x
    modImplementation("com.imeetake.tlib:tlib-fabric:1.5.0-1.21.11")

    // NeoForge 1.21.x
    modImplementation("com.imeetake.tlib:tlib-neoforge:1.5.0-1.21.11")

    // Fabric/Forge 1.20.1
    modImplementation("com.imeetake:tlib-fabric:1.5.0-1.20.1")
    modImplementation("com.imeetake:tlib-forge:1.5.0-1.20.1")
}
```

---

## 📖 Learn more in the Wiki!

Check out the full documentation on GitHub:
👉 [TLib Wiki](https://github.com/iMeeTake/TLib/wiki)
