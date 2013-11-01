package aufgabe3;

import java.io.Serializable;

/**
 * The Unit abstract bin class. Will inherit BaseUnits and DerivedUnits.
 * 
 * @author Patrick Favre-Bulle
 *
 */

abstract class Unit implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -5243111361801852448L;
		private String symbol;
		private String description;
		private String par_name;
		
		/**
		 * CONSTRUCTOR 
		 * 
		 * @param description
		 * @param symbol
		 * @param par_name
		 */
		public Unit(String description, String symbol, String par_name) {
			this.description = description;
			this.symbol = symbol;
			this.par_name = par_name;
		}
		/**
		 * EMPTY CONSTRUCTOR
		 */
		public Unit() {
			//empty instance
		}

		/*
		 * SETTER AND GETTER METHTODS
		 */
		public String getPar_name() {
			return par_name;
		}
		public void setPar_name(String par_name) {
			this.par_name = par_name;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getDescription() {
			return description;
		}
		public String getSymbol() {
			return symbol;
		}
		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
}


