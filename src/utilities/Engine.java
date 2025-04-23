package utilities;

import java.util.List;

public interface Engine
{
  void onHistoryUpdated(List<String> history);
  
  void resizeBig();

  void resizeSmall();
}
