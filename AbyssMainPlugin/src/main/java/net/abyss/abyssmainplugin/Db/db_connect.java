package net.abyss.abyssmainplugin.Db;

import net.abyss.abyssmainplugin.AbyssMainPlugin;
import net.abyss.abyssmainplugin.Manager.GameManager;
import net.abyss.abyssmainplugin.Manager.PlayerManager;
import net.abyss.abyssmainplugin.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class db_connect
{
    private static db_connect instance;

    public static db_connect getInstance()
    {
        if(instance == null)
        {
            synchronized (db_connect.class)
            {
                instance = new db_connect();
            }
        }

        return instance;
    }
    private static AbyssMainPlugin plugin;
    public static void setPlugin(AbyssMainPlugin MainPlugin)
    {
        plugin = MainPlugin;
    }
    public AbyssMainPlugin getPlugin()
    {
        return plugin;
    }

    private Connection connection;

    private String host = "localhost";
    private int port = 3306;
    private String database = "mc_abyss";
    private String table = "playerdata";
    private String username = "root";
    private String password = "cAtQus3Wexia7HU*qVoU";

    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public Connection open_Connection()
    {
        try
        {
            if (connection != null && !connection.isClosed())
            {
                return null;
            }

            synchronized (this)
            {
                if (connection != null && !connection.isClosed())
                {
                    return null;
                }
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
                if(connection == null)
                {
                    Bukkit.shutdown();
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return connection;
    }

    public void SetName(Player player, String name)
    {
        String coloumName = "u_name";
        SetData(player, coloumName, name);
    }

    public String GetName(Player player)
    {
        return PlayerManager.getInstance().getPlayerData(player).getPlayerName();
    }
    public void SetCode(Player player, String gameCode)
    {
        String coloumName = "u_gamecode";
        SetData(player, coloumName, gameCode);
    }

    public void SetStat(Player player, String stat, int value)
    {
        String coloumName = "u_" + stat;
        SetData(player, coloumName, value);
    }
    public int GetStat(Player player, String stat)
    {
        switch (stat)
        {
            case "health":
                return PlayerManager.getInstance().getPlayerData(player).getStatHealth();
            case "strength":
                return PlayerManager.getInstance().getPlayerData(player).getStatStrength();
            case "rapid":
                return PlayerManager.getInstance().getPlayerData(player).getStatRapid();
            case "accel":
                return PlayerManager.getInstance().getPlayerData(player).getStatAccel();
            case "intuition":
                return PlayerManager.getInstance().getPlayerData(player).getStatIntuition();
            case "luck":
                return PlayerManager.getInstance().getPlayerData(player).getStatLuck();
            default:
                return 0;
        }
    }

    public int insertMember(Player player)
    {
        // 예외 처리 안해주고 오류 발생하면 서버 터짐
        Connection conn = null;
        try
        {
            conn = this.open_Connection();
            // 사용자의 정보가 있는지 없는지 확인하는 질의문을 생성한다.
            String sql = "select count(*) from " + table + " u where u.u_uuid = ?";
            // PreparedStatement에 질의어를 넣고
            pstmt = conn.prepareStatement(sql);
            // 질의어에 ?라고 적혀 있는 값을 정의 해준다.
            pstmt.setString(1, player.getUniqueId().toString());
            // 질의어 실행
            rs = pstmt.executeQuery();
            // 검색된 레코드를 넘겨줌 (필수)
            rs.next();
            // 해당하는 유저의 정보가 없는 경우
            if (rs.getInt(1) == 0)
            {
                try
                {
                    // PreparedStatement 초기화 (재사용을 위해)
                    pstmt.clearParameters();
                    sql = "INSERT INTO " + table.toUpperCase() + " (u_uuid, u_name, u_gamecode, u_health, u_strength, u_rapid, u_accel, u_intuition, u_luck) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, player.getUniqueId().toString()); // uuid
                    pstmt.setString(2, player.getName()); // name
                    pstmt.setString(3, GameManager.getInstance().getGameCode());
                    pstmt.setInt(4, 0); // health
                    pstmt.setInt(5, 0); // strength
                    pstmt.setInt(6, 0); // rapid
                    pstmt.setInt(7, 0); // accel
                    pstmt.setInt(8, 0); // intuition
                    pstmt.setInt(9, 0); // luck
                    pstmt.executeUpdate();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return 0;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 1;
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return 2;
            }
        }
    }

    public PlayerData db_PlayerInfo(Player player)
    {
        UUID player_UUID = player.getUniqueId();
        PlayerData playerData = PlayerManager.getInstance().getPlayerData(player);
        Connection conn = null;
        // 예외 처리 안해주면 서버 터짐
        try
        {
            conn = this.open_Connection();
            // 사용자의 정보가 있는지 없는지 확인하는 질의문을 생성한다.
            String sql = "select u.u_no, u.u_uuid, u.u_name, u.u_gamecode, u.u_health, u.u_strength, u.u_rapid, u.u_accel, u.u_intuition, u.u_luck from " + table + " u where u.u_uuid = ?";
            // PreparedStatement에 질의어를 넣고
            pstmt = conn.prepareStatement(sql);
            // 질의어에 ?라고 적혀 있는 값을 정의 해준다.
            pstmt.setString(1, player_UUID.toString());
            // 질의어 실행
            rs = pstmt.executeQuery();
            // 검색된 레코드를 넘겨줌 (필수임 ㅇ)
            rs.next();

            playerData.setPlayerNo(rs.getInt("u_no"));
            playerData.setUUID(rs.getString("u_uuid"));
            playerData.setPlayerName(rs.getString("u_name"));
            playerData.setCode(rs.getString("u_gamecode"));
            playerData.setStatHealth(rs.getInt("u_health"));
            playerData.setStatStrength(rs.getInt("u_strength"));
            playerData.setStatRapid(rs.getInt("u_rapid"));
            playerData.setStatAccel(rs.getInt("u_accel"));
            playerData.setStatIntuition(rs.getInt("u_intuition"));
            playerData.setStatLuck(rs.getInt("u_luck"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                // 연결 된 DB 연결을 종료 해준다. 안해주면 여러개 쌓이면 DB 검색 안됨 (즉, commit과 같은 용도)
                conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return playerData;
    }

    private int SetData(Player player, String coloum, Integer content)
    {
        // 예외 처리 안해주고 오류 발생하면 서버 터짐
        Connection conn = null;
        try
        {
            conn = this.open_Connection();
            // 사용자의 정보가 있는지 없는지 확인하는 질의문을 생성한다.
            String sql = "select count(*) from " + table + " u where u.u_uuid = ?";
            // PreparedStatement에 질의어를 넣고
            pstmt = conn.prepareStatement(sql);
            // 질의어에 ?라고 적혀 있는 값을 정의 해준다.
            pstmt.setString(1, player.getUniqueId().toString());
            // 질의어 실행
            rs = pstmt.executeQuery();
            // 검색된 레코드를 넘겨줌 (필수)
            rs.next();
            // 해당하는 유저의 정보가 없는 경우
            try
            {
                pstmt.clearParameters();
                sql = "UPDATE " + table + " SET " + coloum +  " = " + content + " WHERE u_uuid = \"" + player.getUniqueId() + "\"";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return 0;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 1;
        }
        finally
        {
            try
            {
                conn.close();
                db_PlayerInfo(player);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return 2;
            }
        }
    }

    private int SetData(Player player, String coloum, String content)
    {
        // 예외 처리 안해주고 오류 발생하면 서버 터짐
        Connection conn = null;
        try
        {
            conn = this.open_Connection();
            // 사용자의 정보가 있는지 없는지 확인하는 질의문을 생성한다.
            String sql = "select count(*) from " + table + " u where u.u_uuid = ?";
            // PreparedStatement에 질의어를 넣고
            pstmt = conn.prepareStatement(sql);
            // 질의어에 ?라고 적혀 있는 값을 정의 해준다.
            pstmt.setString(1, player.getUniqueId().toString());
            // 질의어 실행
            rs = pstmt.executeQuery();
            // 검색된 레코드를 넘겨줌 (필수)
            rs.next();
            // 해당하는 유저의 정보가 없는 경우
            try
            {
                pstmt.clearParameters();
                sql = "UPDATE " + table + " SET " + coloum +  " = \"" + content + "\" WHERE u_uuid = \"" + player.getUniqueId() + "\"";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return 0;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 1;
        }
        finally
        {
            try
            {
                conn.close();
                db_PlayerInfo(player);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return 2;
            }
        }
    }
}
