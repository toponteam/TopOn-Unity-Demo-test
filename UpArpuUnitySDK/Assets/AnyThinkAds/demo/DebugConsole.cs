//using UnityEngine;
//using System.Collections;
//
//
//public class DebugConsole : MonoBehaviour
//{
//	
//		
//
//	public GameObject DebugGui = null;
//	public Vector3 defaultGuiPosition = new Vector3(0.01F, 0.98F, 0F);
//	public Vector3 defaultGuiScale = new Vector3(0.5F, 0.5F, 1F);
//	public Color normal = Color.green;
//	public Color warning = Color.yellow;
//	public Color error = Color.red;
//	public int maxMessages = 30; 
//	public float lineSpacing = 0.02F;
//	public ArrayList messages = new ArrayList();
//	public ArrayList guis = new ArrayList();
//	public ArrayList colors = new ArrayList();
//	public bool draggablele = true;
//	public bool visible = true;
//	public bool pixelCorrect = false; 
//
//	public static bool isVisible
//	{                                      
//		get
//		{
//			return DebugConsole.instance.visible;
//		}
//
//		set
//		{
//			DebugConsole.instance.visible = value;
//			if (value == true)
//			{
//				DebugConsole.instance.Display();
//			}
//			else if (value == false)
//			{
//				DebugConsole.instance.ClearScreen();
//			}
//		}
//	}
//
//	public static bool isDraggable
//	{                                      
//		get
//		{
//			return DebugConsole.instance.draggable;
//		}
//
//		set
//		{
//			DebugConsole.instance.draggable = value;
//
//		}
//	}
//
//	private static DebugConsole s_Instance = null;   // Our instance to allow this script to be called without a direct connection.
//	public static DebugConsole instance
//	{
//		get
//		{
//			if (s_Instance == null)
//			{
//				s_Instance = FindObjectOfType(typeof(DebugConsole)) as DebugConsole;
//				if (s_Instance == null)
//				{
//					GameObject console = new GameObject();
//					console.AddComponent<DebugConsole>();
//					console.name = "DebugConsoleController";
//					s_Instance = FindObjectOfType(typeof(DebugConsole)) as DebugConsole;
//					DebugConsole.instance.InitGuis();
//				}
//
//			}
//
//			return s_Instance;
//		}
//	}
//
//	void Awake()
//	{
//		s_Instance = this;
//		InitGuis();
//
//	}
//
//	protected bool guisCreated = false;
//	protected float screenHeight =-1;
//	public void InitGuis()
//	{
//		float usedLineSpacing = lineSpacing;
//		screenHeight = Screen.height;
//		if(pixelCorrect)
//			usedLineSpacing = 1.0F / screenHeight * usedLineSpacing;  
//
//		if (guisCreated == false)
//		{
//			if (DebugGui == null)  // If an external GUIText is not set, provide the default GUIText
//			{
//				DebugGui = new GameObject();
//				DebugGui.AddComponent<GUIText>();
//				DebugGui.name = "DebugGUI(0)";
//				DebugGui.transform.position = defaultGuiPosition;
//				DebugGui.transform.localScale = defaultGuiScale;
//			}
//
//			// Create our GUI objects to our maxMessages count
//			Vector3 position = DebugGui.transform.position;
//			guis.Add(DebugGui);
//			int x = 1;
//
//			while (x < maxMessages)
//			{
//				position.y -= usedLineSpacing;
//				GameObject clone = null;
//				clone = (GameObject)Instantiate(DebugGui, position, transform.rotation);
//				clone.name = string.Format("DebugGUI({0})", x);
//				guis.Add(clone);
//				position = clone.transform.position;
//				x += 1;
//			}
//
//			x = 0;
//			while (x < guis.Count)
//			{
//				GameObject temp = (GameObject)guis[x];
//				temp.transform.parent = DebugGui.transform;
//				x++;
//			}
//			guisCreated = true;
//		} else {
//			// we're called on a screensize change, so fiddle with sizes
//			Vector3 position = DebugGui.transform.position;
//			for(int x=0;x < guis.Count; x++)
//			{
//				position.y -= usedLineSpacing;
//				GameObject temp = (GameObject)guis[x];
//				temp.transform.position= position;
//			}    	
//		}
//	}
//
//
//
//	bool connectedToMouse = false;  
//	void Update()
//	{
//		// If we are visible and the screenHeight has changed, reset linespacing
//		if (visible == true && screenHeight != Screen.height)
//		{
//			InitGuis();
//		}
//		if (draggable == true)
//		{
//			if (Input.GetMouseButtonDown(0))
//			{
//				if (connectedToMouse == false && DebugGui.GetComponent<GUIText>().HitTest((Vector3)Input.mousePosition) == true)
//				{
//					connectedToMouse = true;
//				}
//				else if (connectedToMouse == true)
//				{
//					connectedToMouse = false;
//				}
//
//			}
//
//			if (connectedToMouse == true)
//			{
//				float posX = DebugGui.transform.position.x;
//				float posY = DebugGui.transform.position.y;
//				posX = Input.mousePosition.x / Screen.width;
//				posY = Input.mousePosition.y / Screen.height;
//				DebugGui.transform.position = new Vector3(posX, posY, 0F);
//			}
//		}
//
//	}
//	//+++++++++ INTERFACE FUNCTIONS ++++++++++++++++++++++++++++++++
//	public static void Log(string message, string color)
//	{
//		DebugConsole.instance.AddMessage(message, color);
//
//	}
//	//++++ OVERLOAD ++++
//	public static void Log(string message)
//	{
//		DebugConsole.instance.AddMessage(message);
//	}
//
//	public static void Clear()
//	{
//		DebugConsole.instance.ClearMessages();
//	}
//	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//
//	//---------- void AddMesage(string message, string color) ------
//	//Adds a mesage to the list
//	//--------------------------------------------------------------
//
//	public void AddMessage(string message, string color)
//	{
//		messages.Add(message);
//		colors.Add(color);
//		Display();
//	}
//	//++++++++++ OVERLOAD for AddMessage ++++++++++++++++++++++++++++
//	// Overloads AddMessage to only require one argument(message)
//	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//	public void AddMessage(string message)
//	{
//		messages.Add(message);
//		colors.Add("normal");
//		Display();
//	}
//
//
//	//----------- void ClearMessages() ------------------------------
//	// Clears the messages from the screen and the lists
//	//---------------------------------------------------------------
//	public void ClearMessages()
//	{
//		messages.Clear();
//		colors.Clear();
//		ClearScreen();
//	}
//
//
//	//-------- void ClearScreen() ----------------------------------
//	// Clears all output from all GUI objects
//	//--------------------------------------------------------------
//	void ClearScreen()
//	{
//		if (guis.Count < maxMessages)
//		{
//			//do nothing as we haven't created our guis yet
//		}
//		else
//		{
//			int x = 0;
//			while (x < guis.Count)
//			{
//				GameObject gui = (GameObject)guis[x];   
//				gui.GetComponent<GUIText>().text = "";
//				//increment and loop
//				x += 1;
//			}
//		}
//	}   
//
//
//	//---------- void Prune() ---------------------------------------
//	// Prunes the array to fit within the maxMessages limit
//	//---------------------------------------------------------------
//	void Prune()
//	{
//		int diff;
//		if (messages.Count > maxMessages)
//		{
//			if (messages.Count <= 0)
//			{
//				diff = 0;
//			}
//			else
//			{
//				diff = messages.Count - maxMessages;
//			}
//			messages.RemoveRange(0, (int)diff);
//			colors.RemoveRange(0, (int)diff);
//		}
//
//	}
//
//	//---------- void Display() -------------------------------------
//	// Displays the list and handles coloring
//	//---------------------------------------------------------------
//	void Display()
//	{
//		//check if we are set to display
//		if (visible == false)
//		{
//			ClearScreen();
//		}
//		else if (visible == true)
//		{
//
//
//			if (messages.Count > maxMessages)
//			{
//				Prune();
//			}
//
//			// Carry on with display
//			int x = 0;
//			if (guis.Count < maxMessages)
//			{
//				//do nothing as we havent created our guis yet
//			}
//			else
//			{
//				while (x < messages.Count)
//				{
//					GameObject gui = (GameObject)guis[x];   
//
//					//set our color
//					switch ((string)colors[x])
//					{
//					case "normal": gui.GetComponent<GUIText>().material.color = normal;
//						break;
//					case "warning": gui.GetComponent<GUIText>().material.color = warning;
//						break;
//					case "error": gui.GetComponent<GUIText>().material.color = error;
//						break;
//					}
//
//					//now set the text for this element
//					gui.GetComponent<GUIText>().text = (string)messages[x];
//
//					//increment and loop
//					x += 1;
//				}
//			}
//
//		}
//	}
//
//
//}