package ua.cn.stu.oop.horus.core.domain.gameflow;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.servercommander.ServerCommander;
import ua.cn.stu.oop.horus.core.domain.territory.Battleground;

/**
 *
 * @author alex
 */
public class Mission extends GenericDomain {

    private Battleground battleground;
    private MissionBlock block;
    private ServerCommander serverCommander;
    private WeatherReport weatherReport;
    private MissionEndResult endResult;
    private DBFile file;

    public enum MissionEndResult {

        PLAYED_SUCCESSFULLY,
        WAS_INTERRUPTED
    }

    public Battleground getBattleground() {
        return battleground;
    }

    public void setBattleground(Battleground battleground) {
        this.battleground = battleground;
    }

    public DBFile getFile() {
        return file;
    }

    public void setFile(DBFile file) {
        this.file = file;
    }

    public WeatherReport getWeatherReport() {
        return weatherReport;
    }

    public void setWeatherReport(WeatherReport weatherReport) {
        this.weatherReport = weatherReport;
    }

    public MissionBlock getBlock() {
        return block;
    }

    public void setBlock(MissionBlock block) {
        this.block = block;
    }

    public MissionEndResult getEndResult() {
        return endResult;
    }

    public void setEndResult(MissionEndResult endResult) {
        this.endResult = endResult;
    }

    public ServerCommander getServerCommander() {
        return serverCommander;
    }

    public void setServerCommander(ServerCommander serverCommander) {
        this.serverCommander = serverCommander;
    }
}
