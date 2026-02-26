# MeyPacks Plugin

MeyPacks is a lightweight and simple backpack plugin that provides players with a persistent, portable inventory.

## Features

- Persistent backpacks that save across server restarts.
- Customizable backpack sizes based on permissions.
- In-game backpack icon for easy access.
- Admin commands to view other players' backpacks.
- Robust permission system to control every feature.

## Commands

Here are all the available commands for the MeyPacks plugin.

| Command | Alias | Description | Permission |
| :--- | :--- | :--- | :--- |
| `/meypack` | `/mp` | Opens your personal MeyPack. | `meypack.use` |
| `/meypack open <player>` | `/mp open <player>` | Opens the MeyPack of another online player. | `meypack.admin.open` |

## Permissions

Control every aspect of MeyPacks using the following permissions. These are designed to work with any modern permissions plugin (e.g., LuckPerms).

### Core Permissions

| Permission | Description | Default |
| :--- | :--- | :--- |
| `meypack.use` | Allows a player to use the `/meypack` command to open their own backpack. | `true` (All players) |
| `meypack.haveitem` | Allows a player to receive the backpack icon in their inventory. | `true` (All players) |
| `meypack.keepondeath`| Prevents a player's backpack items from dropping on death. | `false` (Nobody) |
| `meypack.admin.open`| Allows a user to open and modify other players' backpacks with `/meypack open <player>`.| `op` (Operators only) |

### Size Permissions

The size of a player's backpack is determined by the highest `meypack.size` permission they have. If a player has no specific size permission, it defaults to the smallest size.

| Permission | Description | Default |
| :--- | :--- | :--- |
| `meypack.size.1`| Grants a backpack size of **1 row** (9 slots). | `true` (All players) |
| `meypack.size.2`| Grants a backpack size of **2 rows** (18 slots). | `op` |
| `meypack.size.3`| Grants a backpack size of **3 rows** (27 slots). | `op` |
| `meypack.size.4`| Grants a backpack size of **4 rows** (36 slots). | `op` |
| `meypack.size.5`| Grants a backpack size of **5 rows** (45 slots). | `op` |
| `meypack.size.6`| Grants a backpack size of **6 rows** (54 slots). | `op` |
