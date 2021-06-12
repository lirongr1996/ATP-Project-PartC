package View;

import ViewModel.MyViewModel;

import java.io.IOException;

public interface IView{

    public void setViewModel(MyViewModel viewModel);
    public void moveFXML (String name) throws IOException;

}
