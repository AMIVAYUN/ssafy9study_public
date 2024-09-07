package shop.ssafy9study.ssafy9study.Utils;


import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InitService {

    @PostConstruct
    public void MapSetting(){
        String[] strlst = { "Bronze", "Silver", "Gold", "Platinum", "Diamond", "Ruby" };
        Utils.LevelMap.put( "Unrated", 0 );

        for( int i = 0; i < strlst.length; i++ ){
            int idx = 1 + ( 5 * i );
            Utils.LevelMap.put( strlst[ i ], idx );
        }


    }
}
