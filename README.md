![get-disableelytraMARKET](https://user-images.githubusercontent.com/49717977/235355339-21cfac85-bd78-4fc5-b7c3-16c6a341c69a.png)
Informacje:
Plugin pozwala na stworzenie regionu w którym zostaje zablokowane korzystanie z elytry. Elytra po wejściu/wleceniu do strefy zostaje automatycznie ściągnięta do ekwipunku.


Komendy/Commands:
/getelytra create <nazwa> - tworzy strefę na której jest zablokowana elytra | creates zone on which elytra is blocked
/getelytra remove <nazwa> - usuwa strefę na której jest zablokowana elytra | removes the zone on which elytra is blocked
/getelytra selector - gracz otrzymuje selector do zaznaczania regionu | player gets selector to select region
/getelytra reload - przeładowanie pluginu | reloads the plugin


Uprawnienia/Permissions:
getelytra.admin - dostęp do komend admina | access to admin commands
getelytra.bypass - może używać elytry w regionach | can use elytra in the regions


Przykładowa konfiguracja/Example config:
# getelytra.admin - dostep do komend admina | access to admin commands
# getelytra.bypass - może używać elytry w regionach | can use elytra in the regions
regions:
  test:
    first:
      ==: org.bukkit.Location
      world: spawn
      x: 65.0
      y: 120.0
      z: 30.0
      pitch: 0.0
      yaw: 0.0
    second:
      ==: org.bukkit.Location
      world: spawn
      x: 85.0
      y: 148.0
      z: -2.0
      pitch: 0.0
      yaw: 0.0


Ustawianie regionu/Region setup:
1. Użyj komendy /getelytra selector. | Use the /getelytra selector command.
2. Zaznacz teren używając **PRAWEGO** oraz **LEWGO** przycisku myszy. | Select a region using the **RIGHT** and **LEFT** mouse button.
3. Stwórz region używając komendy /getelytra create <nazwa>. | Create a region using the /getelytra create <name> command.
4. Gotowe! | Done!
