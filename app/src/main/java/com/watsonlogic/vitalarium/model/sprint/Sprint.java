package com.watsonlogic.vitalarium.model.sprint;

public class Sprint {
    private String id;
    private long startDate;
    private long endDate;

    public Sprint(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Sprint(String id, long startDate, long endDate){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public static class SprintBuilder{
        private String id;
        private long startDate;
        private long endDate;

        public SprintBuilder setId(String id){
            this.id = id;
            return this;
        }
        public SprintBuilder setStartDate(long startDate){
            this.startDate = startDate;
            return this;
        }
        public SprintBuilder setEndDate(long endDate){
            this.endDate = endDate;
            return this;
        }

        public Sprint build(){
            return new Sprint(id, startDate, endDate);
        }
    }
}
