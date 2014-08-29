package de.saxsys.mvvmfx.contacts;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.cdi.MvvmfxCdiApplication;
import de.saxsys.mvvmfx.contacts.events.TriggerShutdownEvent;
import de.saxsys.mvvmfx.contacts.model.ContactFactory;
import de.saxsys.mvvmfx.contacts.model.CountryFactory;
import de.saxsys.mvvmfx.contacts.model.Repository;
import de.saxsys.mvvmfx.contacts.ui.main.MainView;
import de.saxsys.mvvmfx.contacts.ui.main.MainViewModel;


public class App extends MvvmfxCdiApplication{

	public static void main(String...args){
		launch(args);
	}

	@Inject
	private Repository repository;
	
	@Override 
	public void init() throws Exception {
		super.init();
		
		repository.save(CountryFactory.createGermany());
		repository.save(CountryFactory.createAustria());
		
		repository.save(ContactFactory.createRandomContact());
		repository.save(ContactFactory.createRandomContact());
		repository.save(ContactFactory.createRandomContact());
		repository.save(ContactFactory.createRandomContact());
		repository.save(ContactFactory.createRandomContact());
	}

	@Override 
	public void start(Stage stage) throws Exception {
		makePrimaryStageInjectable(stage);
		
		stage.setTitle("mvvmFX Contacts demo");
		
		ViewTuple<MainView, MainViewModel> main = FluentViewLoader.fxmlView(MainView.class).load();
		
		stage.setScene(new Scene(main.getView()));
		stage.show();
	}
	
	
	public void triggerShutdown(@Observes TriggerShutdownEvent event){
		Platform.exit();	
	}
}
