# Changelog

## [6.5.1](https://github.com/austv-minecraft/TitansBattle/compare/v6.5.0...v6.5.1) (2026-02-07)


### Bug Fixes

* Fix ConcurrentModificationException and event finish issues ([994a201](https://github.com/austv-minecraft/TitansBattle/commit/994a20150345c0a48f27c7019f11f291c5989226))

## [6.5.0](https://github.com/austv-minecraft/TitansBattle/compare/v6.4.4...v6.5.0) (2026-02-01)


### Features

* Move spectator-range config from global to per-game setting ([fa7d41f](https://github.com/austv-minecraft/TitansBattle/commit/fa7d41f33c58d675055b60fcd3c57bc7279a18f8))

## [6.4.4](https://github.com/austv-minecraft/TitansBattle/compare/v6.4.3...v6.4.4) (2026-02-01)


### Bug Fixes

* Add createSourcesJar and shadedArtifactAttached to execution config ([8a0b963](https://github.com/austv-minecraft/TitansBattle/commit/8a0b963e4a1cda283065b4590c672595bf2d0c3e))
* Delete original JAR before upload in workflow ([47c7567](https://github.com/austv-minecraft/TitansBattle/commit/47c7567a3c1e945da6aee653603b54af95826fab))

## [6.4.3](https://github.com/austv-minecraft/TitansBattle/compare/v6.4.2...v6.4.3) (2026-02-01)


### Bug Fixes

* Configure maven-shade to not generate original JAR and fix release workflow pattern ([ad2cd27](https://github.com/austv-minecraft/TitansBattle/commit/ad2cd27a09c19b61d3132bb87ac6bf24c1bcd1ce))

## [6.4.2](https://github.com/austv-minecraft/TitansBattle/compare/v6.4.1...v6.4.2) (2026-02-01)


### Bug Fixes

* NBT-API relocation pattern and spectator mode activation order ([929abca](https://github.com/austv-minecraft/TitansBattle/commit/929abca9e968b574feca28df603d02cdae07c346))

## [6.4.1](https://github.com/austv-minecraft/TitansBattle/compare/v6.4.0...v6.4.1) (2026-02-01)


### Bug Fixes

* Adjust spectator teleportation order in TBCommands ([1c37c32](https://github.com/austv-minecraft/TitansBattle/commit/1c37c329cb66b91b9e27a71f215c3d214944f555))
* Use PAT_TOKEN for release artifact upload ([79d9e65](https://github.com/austv-minecraft/TitansBattle/commit/79d9e6580431f872e37cac431523a2d1db4a87ba))

## [6.4.0](https://github.com/austv-minecraft/TitansBattle/compare/6.3.0...v6.4.0) (2026-01-26)


### Features

* Add automatic release workflow with JAR artifact ([df62314](https://github.com/austv-minecraft/TitansBattle/commit/df623141304aa650bc25e6f46eae3d6361df9efc))
* Add JAR artifact upload to release workflow ([fe5d325](https://github.com/austv-minecraft/TitansBattle/commit/fe5d32517b3e50c4b41ed1631b927b06842f453b))

## [6.2.0](https://github.com/RoinujNosde/TitansBattle/compare/v6.1.0...v6.2.0) (2022-08-09)


### Features

* added hook with Discord for sending game start messages  ([#82](https://github.com/RoinujNosde/TitansBattle/issues/82)) ([993502a](https://github.com/RoinujNosde/TitansBattle/commit/993502a7f4797d59cda1f8240c865353dd7132c7))
* **lang:** new Crowdin updates ([#83](https://github.com/RoinujNosde/TitansBattle/issues/83)) ([9845f7e](https://github.com/RoinujNosde/TitansBattle/commit/9845f7e671276e5ca1bf7c10fd63284e33f07463))


### Bug Fixes

* allowing duplicate elements in casualties ([#80](https://github.com/RoinujNosde/TitansBattle/issues/80)) ([3f9031c](https://github.com/RoinujNosde/TitansBattle/commit/3f9031c76e9d59d3cf93e60d45f0262660f3cdf0))
* ConcurrentModificationException on saveAll task ([a4d5103](https://github.com/RoinujNosde/TitansBattle/commit/a4d5103a992879a62089e6b97f7ef96602ea1958))
* removes out of place reference to SimpleClans ([#85](https://github.com/RoinujNosde/TitansBattle/issues/85)) ([c646061](https://github.com/RoinujNosde/TitansBattle/commit/c646061b86811ab5db97fe1c84a2b5c448f32669))

## [6.1.0](https://github.com/RoinujNosde/TitansBattle/compare/v6.0.0...v6.1.0) (2022-06-30)


### Features

* added items blacklist ([8fe39d7](https://github.com/RoinujNosde/TitansBattle/commit/8fe39d7e68934451225b49811b3447bfc87aa205)), closes [#35](https://github.com/RoinujNosde/TitansBattle/issues/35)
* added items whitelist ([a1be35f](https://github.com/RoinujNosde/TitansBattle/commit/a1be35f98db10233549f1902017a17a3a4c549cc))
* adds a message for explaining the game's objective ([24039b5](https://github.com/RoinujNosde/TitansBattle/commit/24039b52fd46d7e18bb3be21e6b6bed883a67220)), closes [#68](https://github.com/RoinujNosde/TitansBattle/issues/68)
* **lang:** new Crowdin updates ([#67](https://github.com/RoinujNosde/TitansBattle/issues/67)) ([34ffa34](https://github.com/RoinujNosde/TitansBattle/commit/34ffa34e10ce1f7b430ff72abf85a12e552a58c2))
* parses placeholders in commands using PAPI ([5027f6a](https://github.com/RoinujNosde/TitansBattle/commit/5027f6a2399a05c166167b1710e6c008709945c0)), closes [#70](https://github.com/RoinujNosde/TitansBattle/issues/70)


### Bug Fixes

* /tb setwinner would give prizes to everyone still alive ([2ac83f0](https://github.com/RoinujNosde/TitansBattle/commit/2ac83f00450ee7c8c336059e8aac6d5d03dfc5f1)), closes [#69](https://github.com/RoinujNosde/TitansBattle/issues/69)
* added missing %titansbattle_last_<killer|winner>_<game>% to papi info ([#77](https://github.com/RoinujNosde/TitansBattle/issues/77)) ([8858fb8](https://github.com/RoinujNosde/TitansBattle/commit/8858fb8a0c788bf7f041cb504a4e71d21c7ce138))
* checking for groups even on group mode false ([547304f](https://github.com/RoinujNosde/TitansBattle/commit/547304f3298e1aeb0da211bc0b54322134e12017))
* NPE when using challenge commands from console ([65156b4](https://github.com/RoinujNosde/TitansBattle/commit/65156b45fe54d37e572b8d359354150b7d0a2ebb))
* updated placeholders regex to match game names with accents or hyphens ([#74](https://github.com/RoinujNosde/TitansBattle/issues/74)) ([9c279ac](https://github.com/RoinujNosde/TitansBattle/commit/9c279acdc46865608f4b5d74c88172b432a82937))
