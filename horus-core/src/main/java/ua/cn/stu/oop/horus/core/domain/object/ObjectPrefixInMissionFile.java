package ua.cn.stu.oop.horus.core.domain.object;

/**
 *
 * @author alex
 */
public enum ObjectPrefixInMissionFile {

    STATIONARY {

        @Override
        public String toString() {
            return "vehicles.stationary.Stationary";
        }
    },
    ARTILLERY {

        @Override
        public String toString() {
            return "vehicles.artillery.Artillery";
        }
    },
    PLANE {

        @Override
        public String toString() {
            return "vehicles.planes.Plane";
        }
    },
    SHIP {

        @Override
        public String toString() {
            return "ships.Ship";
        }
    },
    HOUSE {

        @Override
        public String toString() {
            return "House";
        }
    }, SMOKE {

        @Override
        public String toString() {
            return "vehicles.stationary.Smoke";
        }
    },
    SEARCHLIGHT {

        @Override
        public String toString() {
            return "vehicles.lights.Searchlight";
        }
    },
    CAMPFIRE {

        @Override
        public String toString() {
            return "vehicles.stationary.Campfire";
        }
    },
    SIREN {

        @Override
        public String toString() {
            return "vehicles.stationary.Siren";
        }
    }
}
