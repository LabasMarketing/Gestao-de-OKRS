import { NextRequest, NextResponse } from 'next/server';

async function handleRequest(request: NextRequest, props: { params: Promise<{ path: string[] }> }) {
  const { path } = await props.params;
  
  // GARANTIA: Se o primeiro item for 'api', a gente remove ele antes de mandar pro backend
  const realPath = path[0] === 'api' ? path.slice(1) : path;
  
  const BACKEND_IP = '172.31.136.143'; 
  const targetUrl = `http://${BACKEND_IP}:25000/${realPath.join('/')}`;

  const method = request.method;

  try {
    const fetchOptions: RequestInit = {
      method,
      headers: {
        'Content-Type': 'application/json',
      },
    };

    if (['POST', 'PUT', 'PATCH'].includes(method)) {
      const body = await request.json();
      fetchOptions.body = JSON.stringify(body);
    }

    const res = await fetch(targetUrl, fetchOptions);

    if (!res.ok) {
      return NextResponse.json(
        { error: `Erro no Backend: ${res.statusText}` },
        { status: res.status }
      );
    }

    const data = await res.json();
    return NextResponse.json(data);

  } catch (error) {
    console.error("Erro no Proxy da API:", error);
    return NextResponse.json(
      { error: 'Não foi possível conectar ao backend na sub-rede privada.' },
      { status: 500 }
    );
  }
}

export const GET = handleRequest;
export const POST = handleRequest;
export const DELETE = handleRequest;
export const PUT = handleRequest;