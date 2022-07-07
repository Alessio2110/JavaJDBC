public class Classroom {

    private long id;
    private String name;
    private Integer capacity;
    private String software;
    private Boolean main_pc;
    private Boolean projector;
    private Boolean is_computerized;
    private boolean is_virtual;

    /**
     *
     * @param id
     * @param name
     * @param capacity
     * @param software
     * @param main_pc
     * @param projector
     * @param is_computerized
     * @param is_virtual
     */
    public Classroom(long id, String name, Integer capacity, String software, Boolean main_pc, Boolean projector, Boolean is_computerized, boolean is_virtual) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.software = software;
        this.main_pc = main_pc;
        this.projector = projector;
        this.is_computerized = is_computerized;
        this.is_virtual = is_virtual;
    }

    public Classroom() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getSoftware() {
        return software;
    }

    public Boolean isMain_pc() {
        return main_pc;
    }

    public Boolean isProjector() {
        return projector;
    }

    public Boolean isIs_computerized() {
        return is_computerized;
    }

    public boolean isIs_virtual() {
        return is_virtual;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", software='" + software + '\'' +
                ", main_pc=" + main_pc +
                ", projector=" + projector +
                ", is_computerized=" + is_computerized +
                ", is_virtual=" + is_virtual +
                '}';
    }
}
