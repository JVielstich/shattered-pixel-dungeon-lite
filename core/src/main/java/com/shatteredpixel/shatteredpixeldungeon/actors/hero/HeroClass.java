/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.QuickSlot;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.Challenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.ElementalStrike;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.Feint;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.SpectralBlades;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.SpiritHawk;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.ElementalBlast;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.WildMagic;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.DeathMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.SmokeBomb;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.Endure;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.HeroicLeap;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.Shockwave;
import com.shatteredpixel.shatteredpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Waterskin;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClothArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.*;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.VelvetPouch;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.*;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.*;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Dagger;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gloves;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Rapier;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WornShortsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingSpike;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.DeviceCompat;

public enum HeroClass {

	WARRIOR( HeroSubClass.BERSERKER, HeroSubClass.GLADIATOR ),
	MAGE( HeroSubClass.BATTLEMAGE, HeroSubClass.WARLOCK ),
	ROGUE( HeroSubClass.ASSASSIN, HeroSubClass.FREERUNNER ),
	HUNTRESS( HeroSubClass.SNIPER, HeroSubClass.WARDEN ),
	DUELIST( HeroSubClass.CHAMPION, HeroSubClass.MONK );

	private HeroSubClass[] subClasses;

	HeroClass( HeroSubClass...subClasses ) {
		this.subClasses = subClasses;
	}

	public void initHero( Hero hero ) {

		hero.heroClass = this;
		Talent.initClassTalents(hero);

		Item i = new ClothArmor().identify();
		if (!Challenges.isItemBlocked(i)) hero.belongings.armor = (ClothArmor)i;

		i = new Food();
		if (!Challenges.isItemBlocked(i)) i.collect();

		new VelvetPouch().collect();
		Dungeon.LimitedDrops.VELVET_POUCH.drop();

		Waterskin waterskin = new Waterskin();
		waterskin.collect();

		// Identify all scrolls
		new ScrollOfIdentify().identify();
		new ScrollOfLullaby().identify();
		new ScrollOfMagicMapping().identify();
		new ScrollOfMirrorImage().identify();
		new ScrollOfRage().identify();
		new ScrollOfRecharging().identify();
		new ScrollOfRemoveCurse().identify();
		new ScrollOfRetribution().identify();
		new ScrollOfTeleportation().identify();
		new ScrollOfTerror().identify();
		new ScrollOfTransmutation().identify();
		new ScrollOfUpgrade().identify();

		// Identify all potions
		new PotionOfExperience().identify();
		new PotionOfFrost().identify();
		new PotionOfHaste().identify();
		new PotionOfHealing().identify();
		new PotionOfInvisibility().identify();
		new PotionOfLevitation().identify();
		new PotionOfLiquidFlame().identify();
		new PotionOfMindVision().identify();
		new PotionOfParalyticGas().identify();
		new PotionOfPurity().identify();
		new PotionOfStrength().identify();
		new PotionOfToxicGas().identify();


		switch (this) {
			case WARRIOR:
				initWarrior( hero );
				break;

			case MAGE:
				initMage( hero );
				break;

			case ROGUE:
				initRogue( hero );
				break;

			case HUNTRESS:
				initHuntress( hero );
				break;

			case DUELIST:
				initDuelist( hero );
				break;
		}

		if (SPDSettings.quickslotWaterskin()) {
			for (int s = 0; s < QuickSlot.SIZE; s++) {
				if (Dungeon.quickslot.getItem(s) == null) {
					Dungeon.quickslot.setSlot(s, waterskin);
					break;
				}
			}
		}

	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
			case DUELIST:
				return Badges.Badge.MASTERY_DUELIST;
		}
		return null;
	}

	private static void initWarrior( Hero hero ) {
		ThrowingStone stones;
		HornOfPlenty horn;

		(hero.belongings.weapon = new WornShortsword()).identify();
		stones = new ThrowingStone();
		stones.quantity(3).collect();
		Dungeon.quickslot.setSlot(0, stones);

		horn = new HornOfPlenty();
		(hero.belongings.artifact = horn).identify();
		hero.belongings.artifact.activate(hero);

		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
		}
	}

	private static void initMage( Hero hero ) {
		MagesStaff staff;
		UnstableSpellbook spellbook;

		staff = new MagesStaff(new WandOfMagicMissile());
		spellbook = new UnstableSpellbook();

		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		(hero.belongings.artifact = spellbook).identify();
		hero.belongings.artifact.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);
	}

	private static void initRogue( Hero hero ) {
		CloakOfShadows cloak;

		(hero.belongings.weapon = new Dagger()).identify();

		cloak = new CloakOfShadows();
		(hero.belongings.artifact = cloak).identify();
		hero.belongings.artifact.activate( hero );

		ThrowingKnife knives = new ThrowingKnife();
		knives.quantity(3).collect();

		Dungeon.quickslot.setSlot(0, cloak);
		Dungeon.quickslot.setSlot(1, knives);
	}

	private static void initHuntress( Hero hero ) {
		SandalsOfNature sandals;

		(hero.belongings.weapon = new Gloves()).identify();

		sandals = new SandalsOfNature();
		(hero.belongings.artifact = sandals).identify();
		hero.belongings.artifact.activate(hero);
		SpiritBow bow = new SpiritBow();
		bow.identify().collect();

		Dungeon.quickslot.setSlot(0, bow);
	}

	private static void initDuelist( Hero hero ) {
		TimekeepersHourglass hourglass;

		(hero.belongings.weapon = new Rapier()).identify();
		hero.belongings.weapon.activate(hero);

		hourglass = new TimekeepersHourglass();
		(hero.belongings.artifact = hourglass).identify();
		hero.belongings.artifact.activate(hero);

		ThrowingSpike spikes = new ThrowingSpike();
		spikes.quantity(2).collect();

		Dungeon.quickslot.setSlot(0, hero.belongings.weapon);
		Dungeon.quickslot.setSlot(1, spikes);
	}

	public String title() {
		return Messages.get(HeroClass.class, name());
	}

	public String desc(){
		return Messages.get(HeroClass.class, name()+"_desc");
	}

	public String shortDesc(){
		return Messages.get(HeroClass.class, name()+"_desc_short");
	}

	public HeroSubClass[] subClasses() {
		return subClasses;
	}

	public ArmorAbility[] armorAbilities(){
		switch (this) {
			case WARRIOR: default:
				return new ArmorAbility[]{new HeroicLeap(), new Shockwave(), new Endure()};
			case MAGE:
				return new ArmorAbility[]{new ElementalBlast(), new WildMagic(), new WarpBeacon()};
			case ROGUE:
				return new ArmorAbility[]{new SmokeBomb(), new DeathMark(), new ShadowClone()};
			case HUNTRESS:
				return new ArmorAbility[]{new SpectralBlades(), new NaturesPower(), new SpiritHawk()};
			case DUELIST:
				return new ArmorAbility[]{new Challenge(), new ElementalStrike(), new Feint()};
		}
	}

	public String spritesheet() {
		switch (this) {
			case WARRIOR: default:
				return Assets.Sprites.WARRIOR;
			case MAGE:
				return Assets.Sprites.MAGE;
			case ROGUE:
				return Assets.Sprites.ROGUE;
			case HUNTRESS:
				return Assets.Sprites.HUNTRESS;
			case DUELIST:
				return Assets.Sprites.DUELIST;
		}
	}

	public String splashArt(){
		switch (this) {
			case WARRIOR: default:
				return Assets.Splashes.WARRIOR;
			case MAGE:
				return Assets.Splashes.MAGE;
			case ROGUE:
				return Assets.Splashes.ROGUE;
			case HUNTRESS:
				return Assets.Splashes.HUNTRESS;
			case DUELIST:
				return Assets.Splashes.DUELIST;
		}
	}
	
	public boolean isUnlocked(){
		//always unlock on debug builds
		if (DeviceCompat.isDebug()) return true;

		switch (this){
			case WARRIOR: default:
				return true;
			case MAGE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_MAGE);
			case ROGUE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_ROGUE);
			case HUNTRESS:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_HUNTRESS);
			case DUELIST:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_DUELIST);
		}
	}
	
	public String unlockMsg() {
		return shortDesc() + "\n\n" + Messages.get(HeroClass.class, name()+"_unlock");
	}

}
