package com.example.gametracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DiskIO {

    public static final String FILE_NAME = "Halcyon_days.sia";
    private static StrawberrySwing strawberrySwing;
    private String path;
    private int num_teams;

    private static DiskIO io_instance;

    private DiskIO(String path){
        this.path = path + "/" + FILE_NAME;
    }

    public static void createInstance(String path){
        if(io_instance == null){
            io_instance = new DiskIO(path);
        }
    }

    public static DiskIO getInstance(){
        return io_instance;
    }

    private void checkNCreate(){
        File f = new File(path);
        if(!f.exists()){
            createFile();
        }
    }

    private void createFile(){
        FileOutputStream fos;
        ObjectOutputStream oos;

        try{
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            StrawberrySwing temp = new StrawberrySwing();
            oos.writeObject(temp);
            oos.flush();
            oos.close();
        }catch (IOException ioe){ throw new RuntimeException("IO Error!");}
    }

    public void InitDiskIO(){
        checkNCreate();
        try{
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            strawberrySwing = (StrawberrySwing)ois.readObject();
            num_teams = strawberrySwing.getTeams().size();
        }catch (IOException ioe){throw new RuntimeException("IO Error!");}
        catch (ClassNotFoundException cfe){throw new RuntimeException("Class Error!");}
    }

    public int getNumTeams(){
        return num_teams;
    }

    public void createTeam(String name,int num_players,String[] player_array){

        int id = num_teams++;
        Team team = new Team(id,num_players,name);
        team.addPlayers(player_array);
        strawberrySwing.getTeams().add(team);

        writeToFile();
    }

    public Team getTeamAt(int index){
        return strawberrySwing.getTeamAt(index);
    }

    public void updateTeam(Team team,int index){
        strawberrySwing.getTeams().set(index,team);
        writeToFile();
    }

    public void writeToFile(){
        FileOutputStream fos;
        ObjectOutputStream oos;

        try{
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(strawberrySwing);
            oos.flush();
            oos.close();
        }catch (IOException ioe){throw new RuntimeException("IO Error!");}
    }
}
