package interfaz.auxiliars;

    import java.util.List;

    public class Evento {
        public final String pregunta;
        public final String optionYes;
        public final String optionNo;
        public final List<Mensaje> mensajes;

        // Constructor simplificado (solo mensajes)
        public Evento(List<Mensaje> mensajes) {
            this.mensajes = mensajes;
            this.pregunta = null;
            this.optionYes = null;
            this.optionNo = null;
        }

        // Constructor completo (para futuro)
        public Evento(String pregunta, String optionYes, String optionNo, List<Mensaje> mensajes) {
            this.pregunta = pregunta;
            this.optionYes = optionYes;
            this.optionNo = optionNo;
            this.mensajes = mensajes;
        }

        public List<Mensaje> getMensajes() {
            return mensajes;
        }

        public String getPregunta() {
            return pregunta;
        }

        public String getOptionYes() {
            return optionYes;
        }

        public String getOptionNo() {
            return optionNo;
        }
    }


