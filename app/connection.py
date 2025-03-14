from huggingface_hub import InferenceClient
from dotenv import load_dotenv, find_dotenv
import os


dotenv_path = find_dotenv()
load_dotenv(dotenv_path)

class Model:

    def __init__(self, attribute1=None, attribute2=None) -> None:
        """
        Constructor Method
        """
        self.attribute1 = attribute1
        self.attribute2 = attribute2

    def client(self, provider="novita", api_key=None) -> InferenceClient:
        """
        Initialize the InferenceClient for API interaction.
        If no API key is provided, it tries to get the value from environment variables.

        Arguments:
        provider (str): The provider name, default is "huggingface".
        api_key (str): Optional API key, if not provided, it fetches from the environment.

        Returns:
        InferenceClient: The initialized client for making requests.
        """
        if not api_key:
            api_key = os.getenv("HF_API_KEY")
            if not api_key:
                raise ValueError("API key is required and not found.")
        
        self.client = InferenceClient(
            provider=provider,
            api_key=api_key
        )
        return self.client

    def message(self, content) -> list:
        """
        Creates a message format for the model request.
        Returns the formatted message to be used in completions.

        Arguments:
        content (str): The content of the message to be sent to the model.

        Returns:
        list: The message list with the user's input.
        """
        messages = [
            {
                "role": "user",
                "content": content
            }
        ]
        return messages  

    def completions(self, model, messages, max_tokens) -> str:
        """
        Ensure the client is initialized before making the API call.
        Returns the completion message from the model or raises an exception if the client is not initialized.

        Arguments:
        model (str): The model to be used for the API request.
        messages (list): The messages to be sent to the model.
        max_tokens (int): The maximum number of tokens to be generated in the response.

        Returns:
        str: The message from the model's response, specifically the generated text.
            The response is accessed using `completion.choices[0].message`, which returns the model's generated message
            from the list of choices. `choices` is a list of potential outputs, and `choices[0].message` gives the first
            (or only) completion's generated text.
        """
        if hasattr(self, 'client'):
            try:
                completion = self.client.chat.completions.create(  
                    model=model, 
                    messages=messages, 
                    max_tokens=max_tokens,
                )
            
               # Extracting and returning the generated content from the response
                return completion['choices'][0]['message']['content']            
            except Exception as e:
                raise Exception(f"Error in completions: {e}")
        else:
            raise Exception("Client not initialized. Please initialize the client first.")
