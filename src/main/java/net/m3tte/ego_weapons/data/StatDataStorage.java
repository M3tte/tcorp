package net.m3tte.ego_weapons.data;

public class StatDataStorage {



    public static DataStat toDataStat(String nbt) {

        DataStat stat = new DataStat();

        switch (nbt.split("\\|")[0]) {
            case "DATASTAT": stat = new SimpleTeleportationData(nbt); break;
        }

        return stat;
    }



}