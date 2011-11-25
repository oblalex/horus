package ua.cn.stu.oop.horus.core.domain.gameflow;

import java.sql.Timestamp;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.territory.Battleground;
import ua.cn.stu.oop.horus.core.weather.*;

/**
 *
 * @author alex
 */
public class WeatherReport extends GenericDomain {

    private Battleground battleground;
    private int cloudsHeightMetres;
    private int windDirectionDegrees;
    private short windSpeedMetresPerSecond;
    private GustType gustType;
    private TurbulenceType turbulenceType;
    private WeatherType weatherType;
    private Timestamp publicationRealTime;
    private Timestamp publicationGameTime;

    public Timestamp getPublicationGameTime() {
        return publicationGameTime;
    }

    public void setPublicationGameTime(Timestamp publicationGameTime) {
        this.publicationGameTime = publicationGameTime;
    }

    public Timestamp getPublicationRealTime() {
        return publicationRealTime;
    }

    public void setPublicationRealTime(Timestamp publicationRealTime) {
        this.publicationRealTime = publicationRealTime;
    }

    public Battleground getBattleground() {
        return battleground;
    }

    public void setBattleground(Battleground battleground) {
        this.battleground = battleground;
    }

    public int getCloudsHeightMetres() {
        return cloudsHeightMetres;
    }

    public void setCloudsHeightMetres(int cloudsHeightMetres) {
        this.cloudsHeightMetres = cloudsHeightMetres;
    }

    public GustType getGustType() {
        return gustType;
    }

    public void setGustType(GustType gustType) {
        this.gustType = gustType;
    }

    public TurbulenceType getTurbulenceType() {
        return turbulenceType;
    }

    public void setTurbulenceType(TurbulenceType turbulenceType) {
        this.turbulenceType = turbulenceType;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    public int getWindDirectionDegrees() {
        return windDirectionDegrees;
    }

    public void setWindDirectionDegrees(int windDirectionDegrees) {
        this.windDirectionDegrees = windDirectionDegrees;
    }

    public short getWindSpeedMetresPerSecond() {
        return windSpeedMetresPerSecond;
    }

    public void setWindSpeedMetresPerSecond(short windSpeedMetresPerSecond) {
        this.windSpeedMetresPerSecond = windSpeedMetresPerSecond;
    }
}
