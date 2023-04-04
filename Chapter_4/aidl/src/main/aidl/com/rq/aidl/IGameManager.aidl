// IGameManager.aidl
package com.rq.aidl;
import com.rq.aidl.Game;

interface IGameManager {

    List<Game> getGameList();

    void addGame(in Game game);
}