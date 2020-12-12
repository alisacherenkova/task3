package com.company.interfaces;

import com.company.RealPoint;
import com.company.interfaces.PixelDrawer;
import com.company.screen.ScreenConverter;

import java.util.List;

public interface IFigure {
    void draw(ScreenConverter sc, PixelDrawer pd); /*Метод, который каждая фигура реализует самостоятельно и рисует себя
    с помощью PixelDrawer'а с применением ScreenConverter'а. Можно несколько развить эту мысль и разработать интерфейс
     IPrimitiveDrawer, который бы скрывал в себе всю логику преобразований и предоставлял бы программисту лишь конечный
     инструмент по рисованию линий.*/
    List<RealPoint> getMarkers(); /*Возвращает координаты маркеров. Может потребоваться создание
    нескольких подобных методов для возвращения разных типов маркера*/
    void moveMarker(RealPoint from, RealPoint to); /*Данный метод должен применять изменения по
    перемещению маркеров. В текущей реализации указываются исходная и конечная точки, а фигура должна сама понять, что произошло*/
    /*Вместо работы с RealPoint'ами, может, лучше работать с ScreenPoint'ами*/
}
