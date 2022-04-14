import pika

def callback(ch, method, props, body):
    print(f"recv: {body}")

creds = pika.PlainCredentials('yuntao', 'EashAnicOc3Op')
params = pika.ConnectionParameters('10.10.10.190', 5672, '/', creds)

with pika.BlockingConnection(params) as conn:
    channel = conn.channel()
    channel.queue_declare("plugin_data", durable=True)
    channel.queue_bind(exchange='plugin_data', queue='plugin_data')
    channel.basic_consume(queue='plugin_data', auto_ack=True, on_message_callback=callback)
    print("consuming...")
    channel.start_consuming()
