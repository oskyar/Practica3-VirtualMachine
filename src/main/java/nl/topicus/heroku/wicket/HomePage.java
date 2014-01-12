/**
  Esta archivo pertenece a la aplicación "generador de objetivos" bajo licencia GPLv2.
  Copyright (C) 2013 Óscar R. Zafra Megías.

  Este programa es software libre. Puede redistribuirlo y/o modificarlo bajo los términos 
  de la Licencia Pública General de GNU según es publicada por la Free Software Foundation, 
  bien de la versión 2 de dicha Licencia o bien (según su elección) de cualquier versión 
  posterior.

  Este programa se distribuye con la esperanza de que sea útil, pero SIN NINGUNA GARANTÍA, 
  incluso sin la garantía MERCANTIL implícita o sin garantizar la CONVENIENCIA PARA UN 
  PROPÓSITO PARTICULAR. Véase la Licencia Pública General de GNU para más detalles.

  Debería haber recibido una copia de la Licencia Pública General junto con este programa. 
  Si no ha sido así, escriba a la Free Software Foundation, Inc., en 675 Mass Ave, Cambridge, 
  MA 02139, EEUU.
 */

package nl.topicus.heroku.wicket;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;
import java.util.Locale;
import java.util.Calendar;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	int objCompletados=0, objNoCompletados=0;

	public HomePage(final PageParameters parameters) throws IOException {
		final ArrayList<String> preguntas = new ArrayList<String>();
		preguntas
				.add("¿Qué tipo de virtualización requiere la modificación de los sistemas operativos?");
		preguntas
				.add("¿Con qué aplicación hemos restringido, reservado, limintado recursos al sistema?");
		preguntas
				.add("¿Qué archivo de configuración teníamos que modificar para dar menos prioridad a los procesos tanto de usuario como de sistema?");
		preguntas
				.add("¿Con qué programa se ve la carga real de los programas/procesos y se pueden comprobar los efectos de migración de tareas pesadas?");
		preguntas
				.add("¿Con qué orden podíamos saber si nuestro pc soporta virtualización?");
		preguntas
				.add("¿Qué gestor de fuentes estamos utilizando actualmente en la asignatura?");

		final ArrayList<String> respuestas = new ArrayList<String>();
		respuestas.add("paravirtualizacion");
		respuestas.add("cgroups");
		respuestas.add("cgcreate");
		respuestas.add("htop");
		respuestas.add("kvm-ok");
		respuestas.add("git");

		final Label feedbackPanel = new Label("feedback", new Model(""));
		add(feedbackPanel);
		feedbackPanel.setOutputMarkupId(true);

		// final int indice = (int)Math.random()*6;
		Locale locale = new Locale("Spain");
		Random r = new Random(
				Calendar.getInstance(locale).getTimeInMillis() * 25 / 100);
		final int indice = r.nextInt(6);
		String pregunta = preguntas.get(indice);
		Label labelPregunta = new Label("pregunta", pregunta);
		Form<?> form = new Form("form");
		form.add(labelPregunta);

		final TextField<String> areaRespuesta = new TextField("respuesta",
				new Model(""));
		areaRespuesta.setOutputMarkupId(true);
		areaRespuesta.setRequired(true);
		form.add(areaRespuesta);
		IndicatingAjaxButton evaluar = new IndicatingAjaxButton("evaluar", form) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				feedbackPanel.setDefaultModelObject(
						"¡¡¡Pero escribe una respuesta!!! ¿o no la sabes?")
						.setOutputMarkupId(true);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				String respuesta = areaRespuesta.getModelObject();
				if (respuesta.equals(respuestas.get(indice))) {
					feedbackPanel
							.setDefaultModelObject(
									"¡Enhorabuena! ¡Has aprendido mucho con JJ!\n Atención a la nueva pregunta")
							.setOutputMarkupId(true);
					feedbackPanel.add(new ClassModifier("fb acierto"));
				} else {
					feedbackPanel.setDefaultModelObject(
							"¡Error! Hay que estudiar un poquito más...")
							.setOutputMarkupId(true);
					feedbackPanel.add(new ClassModifier("fb error"));
				}
				target.add(feedbackPanel, form);
			}
		};
		form.add(evaluar);
		add(form);

		Form<?> formPorcentaje = new Form("formPorcentaje");
		add(formPorcentaje);

		final TextField<String> urlObjetivos = new TextField<String>(
				"urlObjetivos", new Model(""));
		formPorcentaje.add(urlObjetivos);
		final Label feedbackObjetivos = new Label("feedbackObjetivos",
				new Model(""));
		feedbackObjetivos.setOutputMarkupId(true);

		add(feedbackObjetivos);
		urlObjetivos.setOutputMarkupId(true);
		urlObjetivos.setRequired(true);
		
		
		final Label labelObjCumplidos = new Label("objCumplidos", new Model(""));
		final Label labelNoObjCumplidos = new Label("objNoCumplidos", new Model(""));
		labelObjCumplidos.setOutputMarkupId(true);
		labelNoObjCumplidos.setOutputMarkupId(true);
		add(labelNoObjCumplidos);
		add(labelObjCumplidos);
		IndicatingAjaxButton botonPorcentaje = new IndicatingAjaxButton(
				"calcularPorcentaje", formPorcentaje) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				String url = urlObjetivos.getModelObject();
				try {
					objCompletados=0;
					objNoCompletados=0;
					int porcentaje = getContenidoHTML(url);
					feedbackObjetivos.setVisible(true);
					feedbackObjetivos
							.setDefaultModelObject("Porcentaje completado: "
									+ porcentaje + " %");
					labelNoObjCumplidos.setOutputMarkupId(true);
					labelObjCumplidos.setOutputMarkupId(true);
					labelNoObjCumplidos.setDefaultModelObject(objNoCompletados);
					labelNoObjCumplidos.add(new ClassModifier("error"));
					labelObjCumplidos.setDefaultModelObject(objCompletados);
					labelObjCumplidos.add(new ClassModifier("acierto"));
					target.add(labelNoObjCumplidos, labelObjCumplidos);
					if(porcentaje >= 90)
						feedbackObjetivos.add(new ClassModifier("fb acierto"));
					else if(porcentaje >50 || porcentaje < 90){
						feedbackObjetivos.add(new ClassModifier("fb regular"));
					}else{
						feedbackObjetivos.add(new ClassModifier("fb error"));
					}


				} catch (IOException e) {
					//e.printStackTrace();
					feedbackObjetivos
					.setDefaultModelObject("URL mal formada, no existe, o no contiene una lista de objetivos con [x] ó []");
					feedbackObjetivos.add(new ClassModifier("fb error"));
				}
				target.add(feedbackObjetivos);
			}

			@Override
			protected void onError(AjaxRequestTarget arg0, Form<?> arg1) {
				feedbackObjetivos.setDefaultModelObject("errorrrr");
			}
		};
		formPorcentaje.add(botonPorcentaje);

	}

	private int getContenidoHTML(String urlPage) throws IOException {
		URL url = new URL(urlPage);
		URLConnection uc = url.openConnection();
		uc.connect();
		// Creamos el objeto con el que vamos a leer
		BufferedReader in = new BufferedReader(new InputStreamReader(
				uc.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {

			if (inputLine.contains("[x]") || inputLine.contains("[X]"))
				objCompletados++;
			else if (inputLine.contains("[]") || inputLine.contains("[ ]")) {
				objNoCompletados++;
			}
		}
		in.close();
		if(objCompletados + objNoCompletados ==0)
			return (0);
		else
			return (int)((objCompletados / (objCompletados + objNoCompletados)) * 100);
	}
}
