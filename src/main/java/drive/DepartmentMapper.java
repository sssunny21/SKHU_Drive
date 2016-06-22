package drive;

import java.util.List;

public interface DepartmentMapper {
    List<Department> selectAll();
    Department selectD_name(int d_id);
}
