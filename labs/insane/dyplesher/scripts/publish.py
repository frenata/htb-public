import pika

creds = pika.PlainCredentials('yuntao', 'EashAnicOc3Op')
params = pika.ConnectionParameters('10.10.10.190', 5672, '/', creds)

with pika.BlockingConnection(params) as conn:
    channel = conn.channel()
    channel.exchange_declare("plugin_data", durable=True)
    channel.queue_declare("plugin_data", durable=True)
    channel.basic_publish(exchange='plugin_data', routing_key='plugin_data', body='http://127.0.0.1:8500/test.lua')
