package com.google.gwt.maps.client.main;

import test.com.google.gwt.maps.client.AbstractMapsGWTTest;

public class MapImplTest extends AbstractMapsGWTTest {

	@Override
	public LoadLibrary[] getLibraries() {
		return null;
	}

	public void testGetDiv() {
		asyncLibTest(new Runnable() {
			@Override
			public void run() {
				FlowPanel fp = new FlowPanel();
				RootPanel.get().add(fp);

				Element container = fp.getElement();
				String testClassName = "TestClassName";
				container.addClassName(testClassName);

				fp.setSize("103px", "204px");
				Element element = fp.getElement();
				MapOptions options = MapOptions.newInstance();
				MapImpl o = MapImpl.newInstance(element, options);
				Element e = o.getDiv();

				assertEquals(testClassName, e.getClassName());

				finishTest();
			}
		});
	}

	public void testPanTo() {
		asyncLibTest(new Runnable() {
			@Override
			public void run() {
				FlowPanel fp = new FlowPanel();
				RootPanel.get().add(fp);
				Element element = fp.getElement();
				MapOptions options = MapOptions.newInstance();
				MapImpl o = MapImpl.newInstance(element, options);
				LatLng latLng = LatLng.newInstance(35.3d, 38.5d);

				LatLngBounds actual = o.getBounds();
				assertNotNull("non-null bounds expected", actual);

				o.panTo(latLng);
				LatLng latlng = o.getCenter();
				LatLng expected = LatLng.newInstance(35.299999d, 38.499999d);
				assertLatLngEquals(expected, latlng);

				finishTest();
			}
		});
	}

	/**
	 * fitBounds() getBounds()
	 */
	public void testFitBounds() {
		asyncLibTest(new Runnable() {
			@Override
			public void run() {
				FlowPanel fp = new FlowPanel();
				RootPanel.get().add(fp);
				Element element = fp.getElement();
				MapOptions options = MapOptions.newInstance();
				MapImpl o = MapImpl.newInstance(element, options);

				o.setCenter(LatLng.newInstance(41.239045d, -13.508142d));
				o.setZoom(8);

				// test we have good bounds
				LatLngBounds actual = o.getBounds();
				assertNotNull("Map bounds should be non-null", actual);

				LatLng ne = LatLng.newInstance(41.239045d, -13.508142d);
				LatLng sw = LatLng.newInstance(42.88679d, -19.927992d);
				LatLngBounds left = LatLngBounds.newInstance(sw, ne);
				o.fitBounds(left);

				// expected
				LatLng expectedSW = LatLng.newInstance(30.792110d, -180.0d);
				LatLng expectedNE = LatLng.newInstance(51.64751d, 180.0d);
				@SuppressWarnings("unused")
				LatLngBounds expected = LatLngBounds.newInstance(sw, ne);

				// test
				actual = o.getBounds();
				assertNotNull("Map bounds should be non-null", actual);

				assertLatLngEquals(expectedSW, actual.getSouthWest());
				assertLatLngEquals(expectedNE, actual.getNorthEast());

				finishTest();
			}
		});
	}

	public void testGetHeading() {
		asyncLibTest(new Runnable() {
			@Override
			public void run() {
				FlowPanel fp = new FlowPanel();
				RootPanel.get().add(fp);
				Element element = fp.getElement();
				MapOptions options = MapOptions.newInstance();
				MapImpl o = MapImpl.newInstance(element, options);

				// TODO should this return null?
				// try {
				// int heading1 = o.getHeading();
				// } catch (Exception e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				o.setHeading(45);
				int heading2 = o.getHeading();
				assertEquals(45, heading2);
				finishTest();
			}
		});

	}

	public void testgetMapTypeId() {
		asyncLibTest(new Runnable() {
			@Override
			public void run() {
				FlowPanel fp = new FlowPanel();
				RootPanel.get().add(fp);
				Element element = fp.getElement();
				MapOptions options = MapOptions.newInstance();
				MapImpl o = MapImpl.newInstance(element, options);
				MapTypeId left = MapTypeId.HYBRID;
				o.setMapTypeId(left);
				MapTypeId right = o.getMapTypeId();
				assertEquals(left, right);
				finishTest();
			}
		});

	}

	public void testGetMapTypeRegistry() {
		asyncLibTest(new Runnable() {
			@Override
			public void run() {
				FlowPanel fp = new FlowPanel();
				RootPanel.get().add(fp);
				Element element = fp.getElement();
				MapOptions options = MapOptions.newInstance();
				MapImpl o = MapImpl.newInstance(element, options);
				MapTypeId left = MapTypeId.HYBRID;
				o.setMapTypeId(left);

				// custom map to add
				JsArray<MapTypeStyle> styles = ArrayHelper.toJsArray(new MapTypeStyle[] {}); // JS
																								// needs
																								// JSArray,
																								// convert
				StyledMapTypeOptions opt2 = StyledMapTypeOptions.newInstance();
				opt2.setName("My Eyes are Bleeding!");// the name that appears
														// on map controls
				StyledMapType customMapType = StyledMapType.newInstance(styles, opt2); // apply
																						// to
																						// new
																						// styled
																						// map

				// if it worked we should be able to set it
				MapTypeRegistry reg = o.getMapTypeRegistry();
				reg.set("SomeNewMapType", customMapType);

				// we're just making sure nothing broke - all methods are obf
				// and private, so we cannot inspect

				finishTest();
			}
		});

	}

	public void testGetPanBy() {
		asyncLibTest(new Runnable() {
			@Override
			public void run() {
				FlowPanel fp = new FlowPanel();
				RootPanel.get().add(fp);
				Element element = fp.getElement();
				MapOptions options = MapOptions.newInstance();
				MapImpl o = MapImpl.newInstance(element, options);
				o.panBy(50, 52);
				LatLng latlng = o.getCenter();
				assertEquals("(-41.53468367361192, 61.31250000000003)", latlng.getToString());
				finishTest();
			}
		});

	}

	public void testGetProjection() {
		asyncLibTest(new Runnable() {
			@Override
			public void run() {
				FlowPanel fp = new FlowPanel();
				RootPanel.get().add(fp);
				Element element = fp.getElement();
				MapOptions options = MapOptions.newInstance();
				MapImpl o = MapImpl.newInstance(element, options);
				Projection projection = o.getProjection();
				boolean nowrap = false;
				Point pixel = Point.newInstance(10d, 10d);
				Point a = projection.fromPointToLatLng(pixel, nowrap);
				assertEquals("(83.67694304841554, -165.9375)", a.getToString());
				finishTest();
			}
		});

	}

	public void testGetTilt() {
		asyncLibTest(new Runnable() {
			@Override
			public void run() {
				FlowPanel fp = new FlowPanel();
				RootPanel.get().add(fp);
				Element element = fp.getElement();
				MapOptions options = MapOptions.newInstance();
				MapImpl o = MapImpl.newInstance(element, options);
				int tilt = o.getTilt();
				assertEquals(0, tilt);
				o.setTilt(20); // TODO Find out what i need to do for mapOptions
								// to get this workable?
				int t = o.getTilt();
				assertEquals(0, t);
				finishTest();
			}
		});

	}

	// /**
	// * TODO come back and finish this when I have some street view properties
	// to add
	// */
	// public void testStreetView() {
	// asyncLibTest(new Runnable() {
	// public void run() {
	// FlowPanel fpSv = new FlowPanel();
	// RootPanel.get().add(fpSv);
	//
	// FlowPanel fp = new FlowPanel();
	// RootPanel.get().add(fp);
	// Element element = fp.getElement();
	// MapOptions options = MapOptions.newInstance();
	// MapImpl o = MapImpl.newInstance(element, options);
	//
	// StreetViewPanoramaOptions svOptions =
	// StreetViewPanoramaOptions.newInstance();
	// svOptions.setPano("testingPano");
	//
	// StreetViewPanorama left =
	// StreetViewPanorama.newInstance(fpSv.getElement(), svOptions );
	// o.setStreetView(left);
	//
	// StreetViewPanorama right = o.getStreetView();
	//
	// assertEquals(left.getPano(), right.getPano());
	//
	// finishTest();
	// }
	// });

	// }

	public void testGetZoom() {
		asyncLibTest(new Runnable() {
			@Override
			public void run() {
				FlowPanel fp = new FlowPanel();
				RootPanel.get().add(fp);
				Element element = fp.getElement();
				MapOptions options = MapOptions.newInstance();
				MapImpl o = MapImpl.newInstance(element, options);
				int zoom = o.getZoom();
				assertEquals(0, zoom);
				o.setZoom(5);
				assertEquals(5, o.getZoom());
				finishTest();
			}
		});

	}

	public void testJsoToWidgetConversion() {
		asyncLibTest(new Runnable() {
			@Override
			public void run() {

				// make map
				FlowPanel fp = new FlowPanel();
				RootPanel.get().add(fp);
				fp.setSize("103px", "204px");
				@SuppressWarnings("unused")
				Element element = fp.getElement();

				MapOptions options = MapOptions.newInstance();
				MapWidget widget = new MapWidget(options);
				widget.setSize("500px", "500px");
				RootPanel.get().add(widget);

				// use overlay to cast
				TrafficLayer layer = TrafficLayer.newInstance();

				// check null case
				MapWidget acutal = layer.getMap();
				assertNull(acutal);

				// now cast
				layer.setMap(widget);

				// maps should be same
				acutal = layer.getMap();
				assertEquals(widget.getCenter(), acutal.getCenter());

				finishTest();
			}
		});

	}

	public void testPanToBounds() {
		asyncLibTest(new Runnable() {
			@Override
			public void run() {
				FlowPanel fp = new FlowPanel();
				RootPanel.get().add(fp);
				Element element = fp.getElement();
				MapOptions options = MapOptions.newInstance();
				MapImpl o = MapImpl.newInstance(element, options);
				LatLng ne = LatLng.newInstance(41.239045d, -73.508142d);
				LatLng sw = LatLng.newInstance(42.88679d, -69.927992d);
				LatLngBounds left = LatLngBounds.newInstance(sw, ne);
				o.panToBounds(left);
				LatLng center = o.getCenter();
				assertEquals("(32.92888675928454, -8.999999999999968)", center.getToString());
				finishTest();
			}
		});

	}

	public void testSetCenter() {
		asyncLibTest(new Runnable() {
			@Override
			public void run() {
				FlowPanel fp = new FlowPanel();
				RootPanel.get().add(fp);
				Element element = fp.getElement();
				MapOptions options = MapOptions.newInstance();
				MapImpl o = MapImpl.newInstance(element, options);
				LatLng center = LatLng.newInstance(41.239045d, -73.508142d);
				o.setCenter(center);
				assertEquals("(41.239045, -73.50814200000002)", o.getCenter().getToString());
				finishTest();
			}
		});

	}

	@SuppressWarnings("unused")
	public void testUse() {
		asyncLibTest(new Runnable() {
			@Override
			public void run() {
				FlowPanel fp = new FlowPanel();
				RootPanel.get().add(fp);
				Element element = fp.getElement();
				MapOptions options = MapOptions.newInstance();
				MapImpl o = MapImpl.newInstance(element, options);
				finishTest();
			}
		});

	}

	// /**
	// * setControls
	// * getControls
	// */
	// public void testSetControls() {
	// asyncLibTest(new Runnable() {
	// public void run() {
	// FlowPanel fp = new FlowPanel();
	// RootPanel.get().add(fp);
	// Element element = fp.getElement();
	// MapOptions options = MapOptions.newInstance();
	// MapImpl o = MapImpl.newInstance(element, options);
	//
	// MVCArray<Element> controls = null;
	// o.setControls(controls );
	//
	// finishTest();
	// }
	// });

	// }

	// public void testMapTypesRegistry() {
	// asyncLibTest(new Runnable() {
	// public void run() {
	// FlowPanel fp = new FlowPanel();
	// RootPanel.get().add(fp);
	// Element element = fp.getElement();
	// MapOptions options = MapOptions.newInstance();
	// MapImpl o = MapImpl.newInstance(element, options);
	//
	//
	// finishTest();
	// }
	// });

	// }

	// public void testSetOverlayMapTypes() {
	// asyncLibTest(new Runnable() {
	// public void run() {
	// FlowPanel fp = new FlowPanel();
	// RootPanel.get().add(fp);
	// Element element = fp.getElement();
	// MapOptions options = MapOptions.newInstance();
	// MapImpl o = MapImpl.newInstance(element, options);
	//
	//
	// finishTest();
	// }
	// });

	// }
}
