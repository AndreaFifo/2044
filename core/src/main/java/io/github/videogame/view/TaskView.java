package io.github.videogame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.videogame.model.Observer;
import io.github.videogame.model.Task;
import io.github.videogame.model.TaskModel;

import java.util.Map;

public class TaskView implements Observer {
    private Stage stage;
    private Table table;
    private TaskModel taskModel;

    public TaskView(){
        this.stage = new Stage();
        this.table = new Table();
        this.taskModel = TaskModel.getInstance();
        table.top().right();
        table.setFillParent(true);
        stage.addActor(table);
        updateTasks();
    }

    public void updateTasks() {
        table.clear();
        BitmapFont font = new BitmapFont(Gdx.files.internal("Font/font.fnt")); // Assicurati di avere un file di font
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        Task task = taskModel.getCurrentTask();
        Label taskLabel = new Label(task.getTitle(), labelStyle);
        table.clear();
        table.add(taskLabel).left().pad(5);
    }

    public void draw() {
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
    /*
    public void setNextTask(){
        taskModel.setNextTask();
        updateTasks();
    }*/
/*
    @Override
    public void update() {
        taskModel.setNextTask();
        updateTasks();
    }*/
    @Override
    public void update(int id) {
        taskModel.setTask(id);
        updateTasks();
    }
}
