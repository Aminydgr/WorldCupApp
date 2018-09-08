package com.example.amin.maktabprojectworldcupapp.home;

/**
 * Created by Amin on 8/14/2018.
 */

public class HomePresenter implements IHomePresenter {

    private IHomeView homeView;

    public HomePresenter(IHomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void setPage(int pageNumber) {
        switch (pageNumber) {
            case 1:
                homeView.radioPage ();
                break;
            case 2:
                homeView.photoAlbumPage ();
                break;
            case 3:
                homeView.chatRoomPage ();
                break;
            case 4:
                homeView.surveyPage ();
                break;
            case 5:
                homeView.worldCupPage ();
                break;
            case 6:
                homeView.ProfilePage ();
                break;
        }
    }
}
